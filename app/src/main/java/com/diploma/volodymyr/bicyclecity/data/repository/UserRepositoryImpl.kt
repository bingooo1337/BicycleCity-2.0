package com.diploma.volodymyr.bicyclecity.data.repository

import com.diploma.volodymyr.bicyclecity.data.BaseRepository
import com.diploma.volodymyr.bicyclecity.model.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserRepositoryImpl(db: FirebaseFirestore, private val auth: FirebaseAuth)
    : BaseRepository(db), UserRepository {

    override fun createUser(login: String, password: String) {
    }

    override fun loginUser(login: String, password: String) {
    }
}