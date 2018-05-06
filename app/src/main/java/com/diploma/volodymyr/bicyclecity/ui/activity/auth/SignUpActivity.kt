package com.diploma.volodymyr.bicyclecity.ui.activity.auth

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.impl.SignUpPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.auth.SignUpView
import com.diploma.volodymyr.bicyclecity.common.showShortToast
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity(), SignUpView {
    companion object {
        val TAG = SignUpActivity::class.java.simpleName
        fun getIntent(context: Context) = Intent(context, SignUpActivity::class.java)
    }

    @InjectPresenter
    lateinit var signUpPresenter: SignUpPresenter

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initView()
    }

    override fun showLoading() {
        progressDialog = ProgressDialog.show(this, getString(R.string.loading),
                getString(R.string.please_wait), true)
    }

    override fun hideLoading() {
        progressDialog?.cancel()
    }

    override fun showFirstNameError(error: String) {
        firstNameEditText.error = error
    }

    override fun showLastNameError(error: String) {
        lastNameEditText.error = error
    }

    override fun showEmailError(error: String) {
        emailEditText.error = error
    }

    override fun showNumberError(error: String) {
        numberEditText.error = error
    }

    override fun showPasswordError(error: String) {
        passwordEditText.error = error
    }

    override fun closeScreen() {
        finish()
    }

    override fun showToastMessage(message: String) {
        showShortToast(message)
    }

    private fun initView() {
        registerButton.setOnClickListener {
            signUpPresenter.signUp(
                    firstNameEditText.text.toString(),
                    lastNameEditText.text.toString(),
                    emailEditText.text.toString(),
                    numberEditText.text.toString(),
                    passwordEditText.text.toString()
            )
        }
    }
}
