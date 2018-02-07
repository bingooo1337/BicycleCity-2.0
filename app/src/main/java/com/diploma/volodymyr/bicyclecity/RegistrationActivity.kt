package com.diploma.volodymyr.bicyclecity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.diploma.volodymyr.bicyclecity.data.User
import kotlinx.android.synthetic.main.activity_registration.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class RegistrationActivity : AppCompatActivity() {
    companion object {
        const val TAG = "RegistrationActivity"
    }

    private lateinit var db: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        registerButton.setOnClickListener {
            if (validateFields())
                createUser(emailEditText.text.toString(), passwordEditText.text.toString())
        }
    }

    private fun validateFields(): Boolean {
        var validated = true

        if (firstNameEditText.text.isEmpty()) {
            firstNameEditText.error = "Required"
            validated = false
        }

        if (lastNameEditText.text.isEmpty()) {
            lastNameEditText.error = "Required"
            validated = false
        }

        if (emailEditText.text.isEmpty()) {
            emailEditText.error = "Required"
            validated = false
        }

        if (numberEditText.text.isEmpty()) {
            numberEditText.error = "Required"
            validated = false
        }

        if (passwordEditText.text.isEmpty()) {
            passwordEditText.error = "Required"
            validated = false
        }

        return validated
    }

    private fun createUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        showShortToast("You are successfully registered!")
                        firebaseAuth.currentUser?.let { writeUserToDatabase(it) }
                        finish()
                    } else {
                        showShortToast("Registration failed!")
                        Log.e(TAG, it.exception.toString())
                    }
                }
    }

    private fun writeUserToDatabase(user: FirebaseUser) {
        db.collection("users").document(user.uid)
                .set(User(user.email!!,
                        firstNameEditText.text.toString(),
                        lastNameEditText.text.toString(),
                        numberEditText.text.toString()))
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        it.exception?.printStackTrace()
                        showLongToast("Adding to database failed!")
                    }
                }
    }
}
