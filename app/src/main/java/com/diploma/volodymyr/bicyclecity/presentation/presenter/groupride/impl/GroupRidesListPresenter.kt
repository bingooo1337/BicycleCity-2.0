package com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.data.GroupRide
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.IGroupRidesListPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.GroupRidesListView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

@InjectViewState
class GroupRidesListPresenter : BasePresenter<GroupRidesListView>(), IGroupRidesListPresenter {

    private lateinit var db: FirebaseFirestore

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        db = FirebaseFirestore.getInstance()
    }

    override fun loadData() {
        db.collection("group_rides")
                .get()
                .addOnSuccessListener {
                    viewState.showData(it.toObjects(GroupRide::class.java))
                }
    }
}