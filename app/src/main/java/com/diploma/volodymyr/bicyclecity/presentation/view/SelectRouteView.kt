package com.diploma.volodymyr.bicyclecity.presentation.view

import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions

interface SelectRouteView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun activateChoosingPoints()
    fun deactivateChoosingPoints()
    fun openChoosingStart()
    fun openChoosingFinish()
    fun addMarker(coordinates: LatLng, pointName: String, isStart: Boolean)
    fun drawRoute(polyline: PolylineOptions)
    fun setErrorHints(startError: String, finishError: String)
    fun closeScreenWithResult()
}