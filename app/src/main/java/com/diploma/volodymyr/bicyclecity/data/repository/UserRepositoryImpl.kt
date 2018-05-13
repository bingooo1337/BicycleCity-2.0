package com.diploma.volodymyr.bicyclecity.data.repository

import com.diploma.volodymyr.bicyclecity.common.Const
import com.diploma.volodymyr.bicyclecity.data.BaseRepository
import com.diploma.volodymyr.bicyclecity.data.objects.User
import com.diploma.volodymyr.bicyclecity.model.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class UserRepositoryImpl(db: FirebaseFirestore, private val auth: FirebaseAuth)
    : BaseRepository(db), UserRepository {

    override fun createUser(firstName: String, lastName: String, email: String, number: String, password: String,
                            onCompleteListener: (isSuccessful: Boolean, message: String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = it.result.user
                        updateUserDisplayName(user, firstName, lastName)
                                .addOnSuccessListener {
                                    db.collection(Const.USERS).document(user.uid)
                                            .set(User(email, firstName, lastName, number))
                                            .addOnFailureListener { user.delete() }
                                            .addOnCompleteListener {
                                                onCompleteListener.invoke(it.isSuccessful, it.exception?.localizedMessage)
                                            }
                                }
                                .addOnFailureListener { user.delete() }

                    } else
                        onCompleteListener.invoke(it.isSuccessful, it.exception?.localizedMessage)
                }
    }

    override fun loginUser(login: String, password: String) =
            auth.signInWithEmailAndPassword(login, password)

    override fun getCurrentUser() = auth.currentUser

    private fun updateUserDisplayName(user: FirebaseUser, firstName: String, lastName: String) =
            user.updateProfile(
                    UserProfileChangeRequest.Builder()
                            .setDisplayName("$firstName $lastName")
                            .build())
}