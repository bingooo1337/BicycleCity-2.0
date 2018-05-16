package com.diploma.volodymyr.bicyclecity.presentation.presenter.map.impl

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.Marker
import com.diploma.volodymyr.bicyclecity.model.MarkerRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.map.IMapPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.map.MapView
import javax.inject.Inject

@InjectViewState
class MapPresenter : BasePresenter<MapView>(), IMapPresenter {

    companion object {
        private val TAG = MapPresenter::class.java.simpleName

        private const val FILTER_ALL = 0
        private const val FILTER_PARKING = 1
        private const val FILTER_RENT = 2
        private const val FILTER_WORKSHOP = 3
    }

    init {
        dataComponent.inject(this)
    }

    @Inject
    lateinit var repository: MarkerRepository
    private var filter: Int = FILTER_ALL

    override fun loadMarkers() {
        viewState.showLoading()

        repository.getAllMarkers()
                .addSnapshotListener { snapshot, ex ->
                    viewState.hideLoading()

                    ex?.let {
                        viewState.hideLoading()
                        viewState.showToastMessage(R.string.loading_failed)
                        Log.e(TAG, it.message)
                        return@addSnapshotListener
                    }

                    viewState.hideLoading()
                    snapshot?.let {
                        it.toObjects(Marker::class.java).let {
                            viewState.showMarkers(when (filter) {
                                FILTER_PARKING -> it.filter { it.type == Marker.MarkerType.PARKING }
                                FILTER_RENT -> it.filter { it.type == Marker.MarkerType.RENT }
                                FILTER_WORKSHOP -> it.filter { it.type == Marker.MarkerType.WORKSHOP }
                                else -> it
                            }, filter == FILTER_ALL)
                        }
                    }
                }
    }

    override fun filterSelected(itemPosition: Int) {
        filter = itemPosition
        loadMarkers()
    }
}
