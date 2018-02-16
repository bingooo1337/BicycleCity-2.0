package com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.impl

import com.arellomobile.mvp.InjectViewState
import com.diploma.volodymyr.bicyclecity.data.User
import com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.ISignUpPresenter
import com.diploma.volodymyr.bicyclecity.presentation.presenter.base.BasePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.auth.SignUpView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

@InjectViewState
class SignUpPresenter : BasePresenter<SignUpView>(), ISignUpPresenter {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    override fun signUp(firstName: String, lastName: String, email: String, number: String, password: String) {
        if (validate(firstName, lastName, email, number, password)) {
            viewState.showLoading()
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val user = it.user
                        updateUserDisplayName(user, firstName, lastName)
                        addUserToDatabase(user.uid, firstName, lastName, email, number)
                                .addOnFailureListener {
                                    user.delete().addOnSuccessListener {
                                        viewState.hideLoading()
                                        viewState.showToastMessage("Registration failure")
                                    }
                                }
                    }
                    .addOnFailureListener {
                        viewState.hideLoading()
                        if (it.localizedMessage != null) {
                            viewState.showToastMessage(it.localizedMessage)
                        } else {
                            viewState.showToastMessage("Registration failure")
                        }
                    }
        }
    }

    private fun updateUserDisplayName(user: FirebaseUser, firstName: String, lastName: String) {
        user.updateProfile(
                UserProfileChangeRequest.Builder()
                        .setDisplayName("$firstName $lastName")
                        .build())
    }

    private fun addUserToDatabase(uid: String, firstName: String, lastName: String, email: String, number: String) =
            db.collection("users").document(uid)
                    .set(User(email, firstName, lastName, number))
                    .addOnSuccessListener {
                        viewState.hideLoading()
                        viewState.showToastMessage("Registration successful")
                        viewState.closeScreen()
                    }

    private fun validate(firstName: String, lastName: String, email: String, number: String, password: String): Boolean {
        var validated = true

        if (firstName.isEmpty()) {
            viewState.showFirstNameError("Required")
            validated = false
        }
        if (lastName.isEmpty()) {
            viewState.showLastNameError("Required")
            validated = false
        }
        if (email.isEmpty()) {
            viewState.showEmailError("Required")
            validated = false
        }
        if (number.isEmpty()) {
            viewState.showNumberError("Required")
            validated = false
        }
        if (password.isEmpty()) {
            viewState.showPasswordError("Required")
            validated = false
        }

        return validated
    }
}