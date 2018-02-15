package com.diploma.volodymyr.bicyclecity.presentation.presenter.base.impl

import com.arellomobile.mvp.MvpPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

abstract class BasePresenter<T: BaseView> : MvpPresenter<T>() {
    fun showMessage(message: String) {
        viewState.showToastMessage(message)
    }
}
