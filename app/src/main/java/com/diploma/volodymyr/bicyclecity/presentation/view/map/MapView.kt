package com.diploma.volodymyr.bicyclecity.presentation.view.map

import com.diploma.volodymyr.bicyclecity.data.objects.Marker
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

interface MapView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showMarkers(markers: List<Marker>, isZoomNeeded: Boolean)
}