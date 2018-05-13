package com.diploma.volodymyr.bicyclecity.ui.activity.base

import android.annotation.SuppressLint
import com.arellomobile.mvp.MvpAppCompatActivity
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView
import com.diploma.volodymyr.bicyclecity.common.showLongToast

@SuppressLint("Registered")
abstract class BaseActivity : MvpAppCompatActivity(), BaseView {
    override fun showToastMessage(message: String) {
        showLongToast(message)
    }

    override fun showToastMessage(stringId: Int) {
        showLongToast(stringId)
    }
}