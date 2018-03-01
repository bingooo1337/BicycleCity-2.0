package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl

import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.data.GroupRide
import com.diploma.volodymyr.bicyclecity.getGeoPoint
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.ICreateGroupRidePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.CreateGroupRideView
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import java.util.*

@InjectViewState
class CreateGroupRidePresenter : BasePresenter<CreateGroupRideView>(), ICreateGroupRidePresenter {

    private lateinit var db: FirebaseFirestore

    private var start: LatLng? = null
    private var finish: LatLng? = null
    private var date: Date? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        db = FirebaseFirestore.getInstance()
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
        val groupRide = GroupRide(title, desc, date, start!!.getGeoPoint(), finish!!.getGeoPoint())
        db.collection("group_rides")
                .document().set(groupRide)
                .addOnSuccessListener {
                    viewState.showToastMessage("Successful!")
                    viewState.closeScreen()
                }
    }

}
