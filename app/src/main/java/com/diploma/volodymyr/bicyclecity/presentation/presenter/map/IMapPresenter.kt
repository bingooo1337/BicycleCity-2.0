package com.diploma.volodymyr.bicyclecity.presentation.presenter.map

interface IMapPresenter {
    fun loadMarkers()
    fun filterSelected(itemPosition: Int)
}