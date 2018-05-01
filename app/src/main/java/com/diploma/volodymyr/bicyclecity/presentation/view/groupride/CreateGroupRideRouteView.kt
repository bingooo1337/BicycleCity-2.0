package com.diploma.volodymyr.bicyclecity.presentation.view.groupride

import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView
import com.google.android.gms.maps.model.LatLng

interface CreateGroupRideRouteView : BaseView {
    fun activateChoosingPoints()
    fun deactivateChoosingPoints()
    fun openChoosingStart()
    fun openChoosingFinish()
    fun addMarker(coordinates: LatLng, pointName: String, isStart: Boolean)
    fun closeScreenWithResult()
}