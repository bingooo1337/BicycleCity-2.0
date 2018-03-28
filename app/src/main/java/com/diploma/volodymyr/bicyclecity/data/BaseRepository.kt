package com.diploma.volodymyr.bicyclecity.data

import com.google.firebase.firestore.FirebaseFirestore

open class BaseRepository(protected val db: FirebaseFirestore) {
}