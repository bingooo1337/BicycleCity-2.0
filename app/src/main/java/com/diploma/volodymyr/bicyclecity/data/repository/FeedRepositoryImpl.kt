package com.diploma.volodymyr.bicyclecity.data.repository

import com.diploma.volodymyr.bicyclecity.common.Const.EVENTS
import com.diploma.volodymyr.bicyclecity.common.Const.NEWS
import com.diploma.volodymyr.bicyclecity.data.BaseRepository
import com.diploma.volodymyr.bicyclecity.model.FeedRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FeedRepositoryImpl(db: FirebaseFirestore) : BaseRepository(db), FeedRepository {

    override fun getNews(): CollectionReference =
            db.collection(NEWS)

    override fun getEvents(): CollectionReference =
            db.collection(EVENTS)
}