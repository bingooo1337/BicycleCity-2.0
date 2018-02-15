package com.diploma.volodymyr.bicyclecity.ui.activity.base

import android.annotation.SuppressLint
import com.arellomobile.mvp.MvpAppCompatActivity
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView
import com.diploma.volodymyr.bicyclecity.showShortToast

@SuppressLint("Registered")
abstract class BaseActivity : MvpAppCompatActivity(), BaseView {
    override fun showToastMessage(message: String) {
        showShortToast(message)
    }
}