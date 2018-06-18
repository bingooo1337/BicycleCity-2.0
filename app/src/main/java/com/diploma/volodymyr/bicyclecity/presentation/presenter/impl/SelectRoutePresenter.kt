package com.diploma.volodymyr.bicyclecity.presentation.presenter.impl

import android.graphics.Color
import android.util.Log
import com.akexorcist.googledirection.util.DirectionConverter
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.Const.POLYLINE_WIDTH
import com.diploma.volodymyr.bicyclecity.common.getGeoPoint
import com.diploma.volodymyr.bicyclecity.common.subscribe
import com.diploma.volodymyr.bicyclecity.common.toMarkerOptions
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.model.CompetitionRepository
import com.diploma.volodymyr.bicyclecity.model.GroupRideRepository
import com.diploma.volodymyr.bicyclecity.model.UserRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.ISelectRoutePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.SelectRouteView
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.PolyUtil
import com.here.android.mpa.common.*
import com.here.android.mpa.routing.*
import com.here.android.mpa.routing.Route.WHOLE_ROUTE
import javax.inject.Inject

@InjectViewState
class SelectRoutePresenter() :
        BasePresenter<SelectRouteView>(), ISelectRoutePresenter {

    companion object {
        private val TAG = SelectRoutePresenter::class.java.simpleName
        private const val UP = 1
        private const val DOWN = -1
    }

    init {
        App.INSTANSE.getSelectRouteComponent().inject(this)

        if (MapSettings.setIsolatedDiskCacheRootPath("/storage/emulated/0/BicycleCity",
                        App.INSTANSE.getString(R.string.bicycle_city_here_service)))
            MapEngine.getInstance().init(ApplicationContext(App.INSTANSE)) {}
    }

    constructor(groupRide: GroupRide) : this() {
        this.groupRide = groupRide
    }

    constructor(competition: Competition) : this() {
        this.competition = competition
    }

    @Inject
    lateinit var groupRideRepository: GroupRideRepository
    @Inject
    lateinit var competitionRepository: CompetitionRepository
    @Inject
    lateinit var userRepository: UserRepository

    private var groupRide: GroupRide? = null
    private var competition: Competition? = null

    private var start: Place? = null
    private var finish: Place? = null
    private var route: Route? = null

    override fun startPointClicked() {
        viewState.deactivateChoosingPoints()
        viewState.openChoosingStart()
    }

    override fun finishPointClicked() {
        viewState.deactivateChoosingPoints()
        viewState.openChoosingFinish()
    }

    override fun startPointChosen(place: Place) {
        start = place
        start?.let {
            groupRide?.start = it.latLng.getGeoPoint()
            competition?.start = it.latLng.getGeoPoint()
            viewState.addMarkers(it.latLng.toMarkerOptions(true), it.name.toString(),
                    finish?.latLng.toMarkerOptions(false), finish?.name.toString())
        }
        viewState.activateChoosingPoints()
        createDirection(start?.latLng, finish?.latLng)
    }

    override fun finishPointChosen(place: Place) {
        finish = place
        finish?.let {
            groupRide?.finish = it.latLng.getGeoPoint()
            competition?.finish = it.latLng.getGeoPoint()
            viewState.addMarkers(start?.latLng.toMarkerOptions(true), start?.name.toString(),
                    it.latLng.toMarkerOptions(false), it.name.toString())
        }
        viewState.activateChoosingPoints()
        createDirection(start?.latLng, finish?.latLng)
    }

    override fun nothingChosen() {
        viewState.activateChoosingPoints()
    }

    override fun onCreateClicked() {
        val startIsNull = start == null
        val finishIsNull = finish == null
        if (startIsNull || finishIsNull || route == null) {
            App.INSTANSE.getString(R.string.required).let {
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

                val routePlan = RoutePlan()
                routePlan.addWaypoint(RouteWaypoint(GeoCoordinate(start.latitude, start.longitude)))
                routePlan.addWaypoint(RouteWaypoint(GeoCoordinate(finish.latitude, finish.longitude)))
                routePlan.routeOptions = RouteOptions().apply {
                    setTime(groupRide?.date ?: competition!!.date, RouteOptions.TimeType.DEPARTURE)
                    transportMode = RouteOptions.TransportMode.BICYCLE
                    routeType = RouteOptions.Type.SHORTEST
                    isCarpoolAllowed = true
                    routeCount = 1
                    setHighwaysAllowed(true)
                    setParksAllowed(true)
                    setDirtRoadsAllowed(true)
                    setTollRoadsAllowed(true)
                    setTunnelsAllowed(true)
                    setCarShuttleTrainsAllowed(true)
                    setFerriesAllowed(true)
                }

                viewState.showLoading()
                CoreRouter().calculateRoute(routePlan, object : CoreRouter.Listener {
                    override fun onProgress(percent: Int) {}

                    override fun onCalculateRouteFinished(routeResults: MutableList<RouteResult>,
                                                          error: RoutingError) {
                        if (error == RoutingError.NONE) {
                            routeResults.first().route.let {
                                viewState.hideLoading()
                                route = it

                                val duration = it
                                        .getTta(Route.TrafficPenaltyMode.AVOID_LONG_TERM_CLOSURES, WHOLE_ROUTE)
                                        .duration

                                viewState.showDistance(
                                        getDistanceString(it.length.toDouble() / 1000))
                                groupRide?.let {
                                    viewState.showTime(getTimeString(duration / 60 * 14 / it.avgSpeed))
                                }

                                viewState.drawRoute(DirectionConverter.createPolyline(App.INSTANSE,
                                        ArrayList(it.routeGeometry.map { LatLng(it.latitude, it.longitude) }),
                                        POLYLINE_WIDTH, Color.GRAY))

                                val polylines = hashMapOf<Int, MutableList<List<GeoCoordinate>>>(
                                        Pair(UP, arrayListOf()),
                                        Pair(DOWN, arrayListOf()))

                                it.routeGeometryWithElevationData.let {
                                    for (i in 0 until it.size step 5) {
                                        val subList =
                                                if (i + 6 < it.size - 1) it.subList(i, i + 6)
                                                else it.subList(i, it.size - 1)
                                        if (subList.isNotEmpty())
                                            (subList.first().altitude - subList.last().altitude).also {
                                                when {
                                                    it >= subList.size - 2 ->
                                                        polylines[DOWN]?.add(subList)
                                                    it <= (subList.size - 2) * (-1) ->
                                                        polylines[UP]?.add(subList)
                                                }
                                            }
                                    }
                                }
                                polylines[UP]?.forEach {
                                    viewState.drawRoute(DirectionConverter.createPolyline(App.INSTANSE,
                                            ArrayList(it.map { LatLng(it.latitude, it.longitude) }),
                                            POLYLINE_WIDTH, Color.RED))
                                }
                                polylines[DOWN]?.forEach {
                                    viewState.drawRoute(DirectionConverter.createPolyline(App.INSTANSE,
                                            ArrayList(it.map { LatLng(it.latitude, it.longitude) }),
                                            POLYLINE_WIDTH, Color.GREEN))
                                }
                            }
                        } else {
                            viewState.hideLoading()
                            viewState.showToastMessage(R.string.creating_route_failed)
                            Log.e(TAG, error.toString())
                        }
                    }
                })
            }
        }
    }

    private fun createGroupRide(groupRide: GroupRide, route: Route) {
        groupRide.distance = route.length
        route.getTta(Route.TrafficPenaltyMode.AVOID_LONG_TERM_CLOSURES, WHOLE_ROUTE).let {
            groupRide.approximateTime = it.duration / 60 * 14 / groupRide.avgSpeed
        }
        groupRide.encodedRoute = PolyUtil
                .encode(route.routeGeometry.map { LatLng(it.latitude, it.longitude) })

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

    private fun createCompetition(competition: Competition, route: Route) {
        competition.distance = route.length
        competition.encodedRoute = PolyUtil
                .encode(route.routeGeometry.map { LatLng(it.latitude, it.longitude) })

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

    private fun getDistanceString(distance: Double) = App.INSTANSE.let {
        it.getString(R.string.distance) + ": " + it.getString(R.string.km_placeholder, distance)
    }

    private fun getTimeString(time: Int) = App.INSTANSE.let {
        it.getString(R.string.time) + ": " + it.getString(R.string.mins_placeholder, time)
    }
}