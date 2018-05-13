package com.diploma.volodymyr.bicyclecity.data.repository

import com.diploma.volodymyr.bicyclecity.common.Const.MARKERS
import com.diploma.volodymyr.bicyclecity.data.BaseRepository
import com.diploma.volodymyr.bicyclecity.model.MarkerRepository
import com.google.firebase.firestore.FirebaseFirestore

class MarkerRepositoryImpl(db: FirebaseFirestore) : BaseRepository(db), MarkerRepository {

    override fun getAllMarkers() = db.collection(MARKERS).get()
}