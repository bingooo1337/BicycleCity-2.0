package com.diploma.volodymyr.bicyclecity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    companion object {
        val TAG = "LoginActivity"
    }

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance();

        registrationButton.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        loginButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty())
                loginUser(emailEditText.text.toString(), passwordEditText.text.toString())
            else
                Toast.makeText(this, "Enter email & password", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth?.currentUser
    }

    private fun loginUser(email: String, password: String) {
        firebaseAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Login successfully", Toast.LENGTH_LONG).show()
                        val currentUser = firebaseAuth?.currentUser
                        Log.i(TAG, currentUser?.email)
                    } else {
                        Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show()
                        Log.w(TAG, it.exception)
                    }
                }
    }
}
