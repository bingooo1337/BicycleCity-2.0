package com.diploma.volodymyr.bicyclecity.presentation.view.groupride

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView
import com.google.android.gms.maps.model.LatLng
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface CreateGroupRideView : BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun openChoosingStart()

    @StateStrategyType(SkipStrategy::class)
    fun openChoosingFinish()

    fun showMap()
    fun addMarker(coordinates: LatLng, pointName: String, isStart: Boolean)
    fun deactivateChoosingPoints()
    fun activateChoosingPoints()
    fun chooseDate()
    fun chooseTime(calendar: Calendar)
    fun dateTimeChoosed(calendar: Calendar)
}