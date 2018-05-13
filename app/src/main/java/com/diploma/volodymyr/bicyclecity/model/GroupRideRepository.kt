package com.diploma.volodymyr.bicyclecity.model

import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference

interface GroupRideRepository {
    fun getAllGroupRides(): CollectionReference
    fun createGroupRide(groupRide: GroupRide): Task<Void>
}