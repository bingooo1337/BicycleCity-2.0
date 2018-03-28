package com.diploma.volodymyr.bicyclecity.dagger.component

import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsApiModule
import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsDirectionsModule
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl.CreateGroupRidePresenter
import dagger.Component

@Component(modules = [GoogleMapsApiModule::class, GoogleMapsDirectionsModule::class])
interface GoogleMapsComponent {
    fun inject(presenter: CreateGroupRidePresenter)
}