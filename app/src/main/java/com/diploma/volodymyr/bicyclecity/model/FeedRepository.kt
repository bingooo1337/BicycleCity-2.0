package com.diploma.volodymyr.bicyclecity.model

import com.google.firebase.firestore.CollectionReference

interface FeedRepository {
    fun getNews(): CollectionReference
    fun getEvents(): CollectionReference
}