package com.diploma.volodymyr.bicyclecity.presentation.presenter

import com.google.android.gms.location.places.Place

interface ISelectRoutePresenter {
    fun startPointClicked()
    fun startPointChosen(place: Place)
    fun finishPointClicked()
    fun finishPointChosen(place: Place)
    fun nothingChosen()
    fun onCreateClicked()
}