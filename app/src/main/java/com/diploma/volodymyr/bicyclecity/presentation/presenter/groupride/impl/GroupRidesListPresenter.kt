package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.Const.GROUP_RIDES
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.IGroupRidesListPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.GroupRidesListView
import com.google.firebase.firestore.FirebaseFirestore

@InjectViewState
class GroupRidesListPresenter : BasePresenter<GroupRidesListView>(), IGroupRidesListPresenter {

    private lateinit var db: FirebaseFirestore

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        db = FirebaseFirestore.getInstance()
    }

    override fun loadData() {
        viewState.showLoading()

        db.collection(GROUP_RIDES)
                .addSnapshotListener { snapshot, ex ->
                    viewState.hideLoading()

                    if (ex != null) {
                        viewState.showToastMessage(R.string.loading_failed)
                        Log.e(this::class.java.simpleName, ex.localizedMessage)
                        ex.printStackTrace()
                    }

                    snapshot?.let {
                        viewState.showData(it.toObjects(GroupRide::class.java))
                    }
                }
    }
}