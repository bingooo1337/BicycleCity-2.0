package com.diploma.volodymyr.bicyclecity.ui.activity.auth

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.diploma.volodymyr.bicyclecity.presentation.view.auth.SignInView
import com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.impl.SignInPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity
import com.diploma.volodymyr.bicyclecity.ui.activity.navigation.MenuActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity(), SignInView {
    companion object {
        val TAG = SignInActivity::class.java.simpleName
        fun getIntent(context: Context) = Intent(context, SignInActivity::class.java)
    }

    @InjectPresenter
    lateinit var signInPresenter: SignInPresenter

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initView()
    }

    override fun onResume() {
        super.onResume()
        signInPresenter.checkCurrentUser()
    }

    override fun openApp() {
        startActivity(MenuActivity.getIntent(this))
        finish()
    }

    override fun showLoginError(error: String) {
        loginEditText.error = error
    }

    override fun showPasswordError(error: String) {
        passwordEditText.error = error
    }

    override fun showLoading() {
        progressDialog = ProgressDialog.show(this, getString(R.string.loading),
                getString(R.string.please_wait), true)
    }

    override fun hideLoading() {
        progressDialog?.cancel()
    }

    override fun startSignUp() {
        startActivity(SignUpActivity.getIntent(this))
    }

    private fun initView() {
        loginButton.setOnClickListener {
            signInPresenter.signIn(loginEditText.text.toString(), passwordEditText.text.toString())
        }
        registrationButton.setOnClickListener {
            signInPresenter.signUpClicked()
        }
    }
}
