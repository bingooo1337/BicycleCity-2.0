package com.diploma.volodymyr.bicyclecity.presentation.view

import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

interface SelectRouteView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun activateChoosingPoints()
    fun deactivateChoosingPoints()
    fun openChoosingStart()
    fun openChoosingFinish()
    fun clearMap()
    fun addMarkers(markerStart: MarkerOptions? = null, startTitle: String? = null,
                   markerFinish: MarkerOptions? = null, finishTitle: String? = null)

    fun drawRoute(polyline: PolylineOptions)
    fun showDistance(distance: String)
    fun showTime(time: String)
    fun setErrorHints(startError: String, finishError: String)
    fun closeScreenWithResult()
}