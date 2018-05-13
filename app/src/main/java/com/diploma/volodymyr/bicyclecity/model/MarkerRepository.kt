package com.diploma.volodymyr.bicyclecity.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface MarkerRepository {
    fun getAllMarkers(): Task<QuerySnapshot>
}