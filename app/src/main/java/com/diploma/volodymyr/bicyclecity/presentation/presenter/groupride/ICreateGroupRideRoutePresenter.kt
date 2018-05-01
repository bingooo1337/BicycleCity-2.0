package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride

import com.google.android.gms.location.places.Place

interface ICreateGroupRideRoutePresenter {
    fun startPointClicked()
    fun startPointChosen(place: Place)
    fun finishPointClicked()
    fun finishPointChosen(place: Place)
    fun nothingChosen()
    fun createGroupRide()
}