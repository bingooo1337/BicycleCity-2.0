package com.diploma.volodymyr.bicyclecity.presentation.presenter.impl

import android.graphics.Color
import android.util.Log
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.model.Leg
import com.akexorcist.googledirection.request.DirectionOriginRequest
import com.akexorcist.googledirection.util.DirectionConverter
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.Const.POLYLINE_WIDTH
import com.diploma.volodymyr.bicyclecity.common.getGeoPoint
import com.diploma.volodymyr.bicyclecity.common.subscribe
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.model.CompetitionRepository
import com.diploma.volodymyr.bicyclecity.model.GroupRideRepository
import com.diploma.volodymyr.bicyclecity.model.UserRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.ISelectRoutePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.SelectRouteView
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import javax.inject.Inject
import kotlin.math.roundToLong

@InjectViewState
class SelectRoutePresenter() :
        BasePresenter<SelectRouteView>(), ISelectRoutePresenter {

    companion object {
        private val TAG = SelectRoutePresenter::class.java.simpleName
    }

    init {
        App.instance.getSelectRouteComponent().inject(this)
    }

    constructor(groupRide: GroupRide) : this() {
        this.groupRide = groupRide
    }

    constructor(competition: Competition) : this() {
        this.competition = competition
    }

    @Inject
    lateinit var directions: DirectionOriginRequest
    @Inject
    lateinit var groupRideRepository: GroupRideRepository
    @Inject
    lateinit var competitionRepository: CompetitionRepository
    @Inject
    lateinit var userRepository: UserRepository

    private var groupRide: GroupRide? = null
    private var competition: Competition? = null

    private var start: LatLng? = null
    private var finish: LatLng? = null
    private var route: Leg? = null

    override fun startPointClicked() {
        viewState.deactivateChoosingPoints()
        viewState.openChoosingStart()
    }

    override fun finishPointClicked() {
        viewState.deactivateChoosingPoints()
        viewState.openChoosingFinish()
    }

    override fun startPointChosen(place: Place) {
        place.latLng.let {
            start = it
            groupRide?.start = it.getGeoPoint()
            competition?.start = it.getGeoPoint()
            viewState.addMarker(it, place.name.toString(), true)
        }
        viewState.activateChoosingPoints()
        createDirection(start, finish)
    }

    override fun finishPointChosen(place: Place) {
        place.latLng.let {
            finish = it
            groupRide?.finish = it.getGeoPoint()
            competition?.finish = it.getGeoPoint()
            viewState.addMarker(it, place.name.toString(), false)
        }
        viewState.activateChoosingPoints()
        createDirection(start, finish)
    }

    override fun nothingChosen() {
        viewState.activateChoosingPoints()
    }

    override fun onCreateClicked() {
        val startIsNull = start == null
        val finishIsNull = finish == null
        if (startIsNull || finishIsNull || route == null) {
            App.instance.getString(R.string.required).let {
                viewState.setErrorHints(if (startIsNull) it else "", if (finishIsNull) it else "")
            }
            return
        }

        route?.let { route ->
            viewState.showLoading()

            if (groupRide == null)
                competition?.let { createCompetition(it, route) }
            else
                groupRide?.let { createGroupRide(it, route) }
        }
    }

    private fun createDirection(startPoint: LatLng?, finishPoint: LatLng?) {
        startPoint?.let { start ->
            finishPoint?.let { finish ->
                viewState.showLoading()
                directions
                        .from(start)
                        .to(finish)
                        .transportMode(TransportMode.DRIVING)
                        .execute(object : DirectionCallback {
                            override fun onDirectionSuccess(direction: Direction, rawBody: String) {
                                direction.routeList.first().legList.first().let {
                                    viewState.hideLoading()
                                    viewState.drawRoute(DirectionConverter.createPolyline(
                                                    App.instance, it.directionPoint, POLYLINE_WIDTH, Color.CYAN))
                                    route = it
                                }
                            }

                            override fun onDirectionFailure(t: Throwable) {
                                viewState.hideLoading()
                                viewState.showToastMessage(R.string.creating_route_failed)
                                Log.e(TAG, t.message)
                                t.printStackTrace()
                            }
                        })
            }
        }
    }

    private fun createGroupRide(groupRide: GroupRide, route: Leg) {
        groupRide.distance = route.distance.value.toDouble()
        groupRide.approximateTime = (groupRide.distance / 333).roundToLong()
        groupRide.encodedRoute = PolyUtil.encode(route.directionPoint)

        userRepository.getCurrentUser()?.let {
            groupRide.creatorId = it.uid
            groupRide.users?.add(it.uid)
        }

        groupRideRepository.createGroupRide(groupRide)
                .subscribe({ _ ->
                    viewState.closeScreenWithResult()
                }, {
                    viewState.hideLoading()
                    viewState.showToastMessage(R.string.failed)
                    Log.e(TAG, it.message)
                    it.printStackTrace()
                })
    }

    private fun createCompetition(competition: Competition, route: Leg) {
        competition.distance = route.distance.value.toDouble()
        competition.encodedRoute = PolyUtil.encode(route.directionPoint)

        userRepository.getCurrentUser()?.let {
            competition.creatorId = it.uid
            competition.users.add(it.uid)
        }

        competitionRepository.createCompetition(competition)
                .subscribe({
                    viewState.closeScreenWithResult()
                }, {
                    viewState.hideLoading()
                    viewState.showToastMessage(R.string.failed)
                    Log.e(TAG, it.message)
                    it.printStackTrace()
                })
    }
}