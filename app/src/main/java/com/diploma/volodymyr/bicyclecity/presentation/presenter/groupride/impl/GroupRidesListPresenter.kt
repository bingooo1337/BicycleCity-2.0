package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl

import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.Const.GROUP_RIDES
import com.diploma.volodymyr.bicyclecity.data.model.GroupRide
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
        db.collection(GROUP_RIDES)
                .get()
                .addOnSuccessListener { viewState.showData(it.toObjects(GroupRide::class.java)) }
    }
}