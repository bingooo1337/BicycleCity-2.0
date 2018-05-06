package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl

import android.util.Log
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.request.DirectionOriginRequest
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.common.getGeoPoint
import com.diploma.volodymyr.bicyclecity.model.GroupRideRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.ICreateGroupRideRoutePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.CreateGroupRideRouteView
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import kotlin.math.roundToLong

@InjectViewState
class CreateGroupRideRoutePresenter(private val groupRide: GroupRide) :
        BasePresenter<CreateGroupRideRouteView>(), ICreateGroupRideRoutePresenter {

    companion object {
        private val TAG = CreateGroupRideRoutePresenter::class.java.simpleName
    }

    init {
        App.instance.getCreateGroupRideComponent()
    }

    @Inject
    lateinit var directions: DirectionOriginRequest
    @Inject
    lateinit var repository: GroupRideRepository

    private var start: LatLng? = null
    private var finish: LatLng? = null

    override fun startPointClicked() {
        viewState.deactivateChoosingPoints()
        viewState.openChoosingStart()
    }

    override fun startPointChosen(place: Place) {
        start = place.latLng
        viewState.addMarker(place.latLng, place.name.toString(), true)
        viewState.activateChoosingPoints()
    }

    override fun finishPointClicked() {
        viewState.deactivateChoosingPoints()
        viewState.openChoosingFinish()
    }

    override fun finishPointChosen(place: Place) {
        finish = place.latLng
        viewState.addMarker(place.latLng, place.name.toString(), false)
        viewState.activateChoosingPoints()
    }

    override fun nothingChosen() {
        viewState.activateChoosingPoints()
    }

    override fun createGroupRide() {
        var calcDistance: Double
        var time: Long
        directions
                .from(start!!)
                .to(finish!!)
                .transportMode(TransportMode.DRIVING)
                .execute(object : DirectionCallback {
                    override fun onDirectionSuccess(direction: Direction, rawBody: String) {
                        calcDistance = direction.routeList[0].legList[0].distance.value.toDouble()
                        time = (calcDistance / 333).roundToLong()

                        with(groupRide) {
                            start = this@CreateGroupRideRoutePresenter.start!!.getGeoPoint()
                            finish = this@CreateGroupRideRoutePresenter.finish!!.getGeoPoint()
                            distance = calcDistance
                            approximateTime = time
                        }

                        repository.createGroupRide(groupRide)
                                .addOnSuccessListener {
//                                    viewState.closeScreen()
                                }
                                .addOnFailureListener {
                                    viewState.showToastMessage(R.string.failed)
                                    Log.e(TAG, it.message)
                                    it.printStackTrace()
                                }
                    }

                    override fun onDirectionFailure(t: Throwable) {
                        Log.e(TAG, t.message)
                        t.printStackTrace()
                    }
                })
    }
}