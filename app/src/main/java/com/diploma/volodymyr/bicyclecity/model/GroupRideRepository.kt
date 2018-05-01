package com.diploma.volodymyr.bicyclecity.model

import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface GroupRideRepository {
    fun getAllGroupRides(): Task<QuerySnapshot>
    fun createGroupRide(groupRide: GroupRide): Task<Void>
}