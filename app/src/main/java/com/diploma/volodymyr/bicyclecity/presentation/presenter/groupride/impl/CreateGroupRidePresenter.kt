package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl

import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.ICreateGroupRidePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.CreateGroupRideView
import com.google.android.gms.location.places.Place
import java.util.*

@InjectViewState
class CreateGroupRidePresenter : BasePresenter<CreateGroupRideView>(), ICreateGroupRidePresenter {

    override fun startPointClicked() {
        viewState.deactivateChoosingPoints()
        viewState.openChoosingStart()
    }

    override fun startPointChoosed(place: Place) {
        viewState.addMarker(place.latLng, place.name.toString(), true)
        viewState.activateChoosingPoints()
    }

    override fun finishPointClicked() {
        viewState.deactivateChoosingPoints()
        viewState.openChoosingFinish()
    }

    override fun finishPointChoosed(place: Place) {
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
        viewState.dateTimeChoosed(calendar)
    }

}
