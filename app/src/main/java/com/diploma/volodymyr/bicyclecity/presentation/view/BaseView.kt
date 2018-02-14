package com.diploma.volodymyr.bicyclecity.presentation.view

import com.arellomobile.mvp.MvpView

interface BaseView : MvpView {
    fun showToastMessage(message: String)
}