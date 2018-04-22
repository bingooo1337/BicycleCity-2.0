package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl

import android.util.Log
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.request.DirectionOriginRequest
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.App
import com.diploma.volodymyr.bicyclecity.Const.GROUP_RIDES
import com.diploma.volodymyr.bicyclecity.data.model.GroupRide
import com.diploma.volodymyr.bicyclecity.getGeoPoint
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.ICreateGroupRidePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.CreateGroupRideView
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToLong

@InjectViewState
class CreateGroupRidePresenter : BasePresenter<CreateGroupRideView>(), ICreateGroupRidePresenter {

    @Inject
    lateinit var directions: DirectionOriginRequest
    private lateinit var db: FirebaseFirestore

    private var start: LatLng? = null
    private var finish: LatLng? = null
    private var date: Date? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        db = FirebaseFirestore.getInstance()
        App.instance.getGoogleMapsComponent().inject(this)
    }

    override fun startPointClicked() {
        viewState.deactivateChoosingPoints()
        viewState.openChoosingStart()
    }

    override fun startPointChoosed(place: Place) {
        start = place.latLng
        viewState.addMarker(place.latLng, place.name.toString(), true)
        viewState.activateChoosingPoints()
    }

    override fun finishPointClicked() {
        viewState.deactivateChoosingPoints()
        viewState.openChoosingFinish()
    }

    override fun finishPointChoosed(place: Place) {
        finish = place.latLng
        viewState.addMarker(place.latLng, place.name.toString(), false)
        viewState.activateChoosingPoints()
    }

    override fun nothingChoosed() {
        viewState.activateChoosingPoints()
    }

    override fun chooseDateAndTime() {
        viewState.chooseDate()
    }

    override fun dateChoosed(calendar: Calendar) {
        viewState.chooseTime(calendar)
    }

    override fun timeChoosed(calendar: Calendar) {
        date = calendar.time
        viewState.dateTimeChoosed(calendar)
    }

    override fun createGroupRide(title: String, desc: String) {
//        var distance: Double = 0.0
//        var time: Long = 0
//        directions
//                .from(start!!)
//                .to(finish!!)
//                .transportMode(TransportMode.DRIVING)
//                .execute(object : DirectionCallback {
//                    override fun onDirectionSuccess(direction: Direction, rawBody: String) {
//                        distance = direction.routeList[0].legList[0].distance.value.toDouble()
//                        time = (distance / 333).roundToLong()
//                        val groupRide = GroupRide(title, desc, date, start!!.getGeoPoint(), finish!!.getGeoPoint(),
//                                distance, time)
//                        db.collection(GROUP_RIDES)
//                                .document().set(groupRide)
//                                .addOnSuccessListener {
//                                    viewState.showToastMessage("Successful!")
//                                    viewState.closeScreen()
//                                }
//                    }
//
//                    override fun onDirectionFailure(t: Throwable) {
//                        Log.e(this::class.java.simpleName, t.message)
//                        t.printStackTrace()
//                    }
//                })
    }
}
