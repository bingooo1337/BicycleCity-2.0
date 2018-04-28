package com.diploma.volodymyr.bicyclecity.presentation.presenter.base

import com.arellomobile.mvp.MvpPresenter
import com.diploma.volodymyr.bicyclecity.App
import com.diploma.volodymyr.bicyclecity.dagger.component.DataComponent
import com.diploma.volodymyr.bicyclecity.dagger.component.GoogleMapsComponent
import com.diploma.volodymyr.bicyclecity.presentation.view.BaseView

abstract class BasePresenter<T : BaseView> : MvpPresenter<T>() {

    protected var dataComponent: DataComponent = App.instance.getDataComponent()
    protected var googleMapsComponent: GoogleMapsComponent = App.instance.getGoogleMapsComponent()

    fun showMessage(message: String) {
        viewState.showToastMessage(message)
    }
}
