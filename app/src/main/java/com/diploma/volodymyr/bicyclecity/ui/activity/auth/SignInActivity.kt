package com.diploma.volodymyr.bicyclecity.ui.activity.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.diploma.volodymyr.bicyclecity.presentation.view.auth.SignInView
import com.diploma.volodymyr.bicyclecity.presentation.presenter.auth.SignInPresenter

import com.arellomobile.mvp.MvpActivity


import com.arellomobile.mvp.presenter.InjectPresenter

class SignInActivity : MvpActivity(), SignInView {
    @InjectPresenter
    internal var mSignInPresenter: SignInPresenter? = null


    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    companion object {
        val TAG = "SignInActivity"

        fun getIntent(context: Context): Intent {

            return Intent(context, SignInActivity::class.java)
        }
    }
}
