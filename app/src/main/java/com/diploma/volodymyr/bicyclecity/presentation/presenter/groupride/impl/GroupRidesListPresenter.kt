package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.model.GroupRideRepository
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.IGroupRidesListPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.GroupRidesListView
import javax.inject.Inject

@InjectViewState
class GroupRidesListPresenter : BasePresenter<GroupRidesListView>(), IGroupRidesListPresenter {

    init {
        dataComponent.inject(this)
    }

    @Inject
    lateinit var repository: GroupRideRepository
    private lateinit var groupRides: List<GroupRide>

    override fun loadData() {
        viewState.showLoading()

        repository.getAllGroupRides()
                .addSnapshotListener { snapshot, ex ->
                    viewState.hideLoading()

                    if (ex != null) {
                        viewState.showToastMessage(R.string.loading_failed)
                        Log.e(this::class.java.simpleName, ex.localizedMessage)
                        ex.printStackTrace()
                        return@addSnapshotListener
                    }

                    snapshot?.let {
                        groupRides = it.documents
                                .mapNotNull { it.toObject(GroupRide::class.java).apply { this?.id = it.id } }
                        viewState.showData(groupRides)
                    }
                }
    }

    override fun onGroupRideClicked(groupRide: GroupRide) {
        viewState.openGroupRide(groupRide.title, groupRide.id)
    }
}