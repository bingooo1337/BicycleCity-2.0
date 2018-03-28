package com.diploma.volodymyr.bicyclecity.model

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    fun createUser(firstName: String, lastName: String, email: String, number: String, password: String,
                   onCompleteListener: (isSuccessful: Boolean, message: String?) -> Unit)

    fun loginUser(login: String, password: String): Task<AuthResult>
    fun getCurrentUser(): FirebaseUser?
}