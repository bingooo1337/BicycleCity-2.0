package com.diploma.volodymyr.bicyclecity.presentation.presenter.base.impl

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

@InjectViewState
class BasePresenter : MvpPresenter<BaseView>() {
    fun showMessage(message: String) {
        viewState.showToastMessage(message)
    }
}