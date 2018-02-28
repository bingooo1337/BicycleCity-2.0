package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride

import com.google.android.gms.location.places.Place
import java.util.*

interface ICreateGroupRidePresenter {
    fun startPointClicked()
    fun startPointChoosed(place: Place)
    fun finishPointClicked()
    fun finishPointChoosed(place: Place)
    fun nothingChoosed()
    fun chooseDateAndTime()
    fun dateChoosed(calendar: Calendar)
    fun timeChoosed(calendar: Calendar)
}