package com.diploma.volodymyr.bicyclecity.data.repository

import com.diploma.volodymyr.bicyclecity.Const.GROUP_RIDES
import com.diploma.volodymyr.bicyclecity.data.BaseRepository
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.model.GroupRideRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class GroupRideRepositoryImpl(db: FirebaseFirestore): BaseRepository(db), GroupRideRepository {

    override fun getAllGroupRides(): Task<QuerySnapshot> =
            db.collection(GROUP_RIDES).get()

    override fun createGroupRide(groupRide: GroupRide): Task<Void> =
            db.collection(GROUP_RIDES).document().set(groupRide)
}