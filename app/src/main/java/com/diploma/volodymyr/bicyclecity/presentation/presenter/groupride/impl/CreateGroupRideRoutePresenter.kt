package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl

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
import com.diploma.volodymyr.bicyclecity.model.GroupRideRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.ICreateGroupRideRoutePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.CreateGroupRideRouteView
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import javax.inject.Inject
import kotlin.math.roundToLong

@InjectViewState
class CreateGroupRideRoutePresenter(private val groupRide: GroupRide) :
        BasePresenter<CreateGroupRideRouteView>(), ICreateGroupRideRoutePresenter {

    companion object {
        private val TAG = CreateGroupRideRoutePresenter::class.java.simpleName
    }

    init {
        App.instance.getCreateGroupRideComponent().inject(this)
    }

    @Inject
    lateinit var directions: DirectionOriginRequest
    @Inject
    lateinit var repository: GroupRideRepository

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
            groupRide.start = it.getGeoPoint()
            viewState.addMarker(it, place.name.toString(), true)
        }
        viewState.activateChoosingPoints()
        createDirection(start, finish)
    }

    override fun finishPointChosen(place: Place) {
        place.latLng.let {
            finish = it
            groupRide.finish = it.getGeoPoint()
            viewState.addMarker(it, place.name.toString(), false)
        }
        viewState.activateChoosingPoints()
        createDirection(start, finish)
    }

    override fun nothingChosen() {
        viewState.activateChoosingPoints()
    }

    override fun createGroupRide() {
        val startIsNull = start == null
        val finishIsNull = finish == null
        if (startIsNull || finishIsNull || route == null) {
            App.instance.getString(R.string.required).let {
                viewState.setErrorHints(if (startIsNull) it else "", if (finishIsNull) it else "")
            }
            return
        }

        route?.let {
            viewState.hideLoading()
            groupRide.distance = it.distance.value.toDouble()
            groupRide.approximateTime = (groupRide.distance / 333).roundToLong()
            groupRide.encodedRoute = PolyUtil.encode(it.directionPoint)

            repository.createGroupRide(groupRide)
                    .subscribe({ _ ->
                        viewState.closeScreenWithResult()
                    }, {
                        viewState.hideLoading()
                        viewState.showToastMessage(R.string.failed)
                        Log.e(TAG, it.message)
                        it.printStackTrace()
                    })
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
                                            App.instance, it.directionPoint, POLYLINE_WIDTH, Color.RED))
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
}