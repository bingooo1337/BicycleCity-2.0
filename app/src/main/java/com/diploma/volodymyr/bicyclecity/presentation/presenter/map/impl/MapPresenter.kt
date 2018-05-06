package com.diploma.volodymyr.bicyclecity.presentation.presenter.map.impl

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.Marker
import com.diploma.volodymyr.bicyclecity.model.MarkerRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.map.IMapPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.map.MapView
import com.diploma.volodymyr.bicyclecity.common.subscribe
import javax.inject.Inject

@InjectViewState
class MapPresenter : BasePresenter<MapView>(), IMapPresenter {

    companion object {
        private val TAG = MapPresenter::class.java.simpleName
    }

    init {
        dataComponent.inject(this)
    }

    @Inject
    lateinit var repository: MarkerRepository

    override fun loadMarkers() {
        viewState.showLoading()

        repository.getAllMarkers()
                .subscribe({
                    viewState.hideLoading()
                    viewState.showMarkers(it.toObjects(Marker::class.java))
                }, {
                    viewState.hideLoading()
                    viewState.showToastMessage(R.string.loading_failed)
                    Log.e(TAG, it.message)
                })
    }
}
