package com.diploma.volodymyr.bicyclecity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*
import com.google.firebase.auth.FirebaseAuth


class RegistrationActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        firebaseAuth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty())
                createUser(emailEditText.text.toString(), passwordEditText.text.toString())
            else
                Toast.makeText(this, "Enter email & password", Toast.LENGTH_LONG).show()
        }
    }

    private fun createUser(email: String, password: String) {
        firebaseAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "You are successfully registered!",
                                Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Authentication failed!",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }
}
