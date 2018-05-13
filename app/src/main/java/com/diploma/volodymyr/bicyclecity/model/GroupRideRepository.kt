package com.diploma.volodymyr.bicyclecity.model

import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

interface GroupRideRepository {
    fun getAllGroupRides(): CollectionReference
    fun getGroupRideById(id: String): DocumentReference
    fun createGroupRide(groupRide: GroupRide): Task<Void>
    fun updateGroupRide(groupRideId: String, groupRide: GroupRide): Task<Void>
}