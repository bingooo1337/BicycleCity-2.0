package com.diploma.volodymyr.bicyclecity.model

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    fun createUser(login: String, password: String)
    fun loginUser(login: String, password: String): Task<AuthResult>
    fun getCurrentUser(): FirebaseUser?
}