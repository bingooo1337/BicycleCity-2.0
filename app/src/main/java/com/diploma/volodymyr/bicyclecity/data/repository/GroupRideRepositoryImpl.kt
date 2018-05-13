package com.diploma.volodymyr.bicyclecity.data.repository

import com.diploma.volodymyr.bicyclecity.common.Const.GROUP_RIDES
import com.diploma.volodymyr.bicyclecity.data.BaseRepository
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.model.GroupRideRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class GroupRideRepositoryImpl(db: FirebaseFirestore) : BaseRepository(db), GroupRideRepository {

    override fun getAllGroupRides(): CollectionReference =
            db.collection(GROUP_RIDES)

    override fun getGroupRideById(id: String): DocumentReference =
            db.collection(GROUP_RIDES).document(id)

    override fun createGroupRide(groupRide: GroupRide): Task<Void> =
            db.collection(GROUP_RIDES).document().set(groupRide)

    override fun updateGroupRide(groupRideId: String, groupRide: GroupRide): Task<Void> =
            db.collection(GROUP_RIDES).document(groupRideId).set(groupRide)
}