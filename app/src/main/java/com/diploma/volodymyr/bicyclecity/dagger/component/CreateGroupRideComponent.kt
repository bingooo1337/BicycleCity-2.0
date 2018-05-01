package com.diploma.volodymyr.bicyclecity.dagger.component

import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsApiModule
import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsDirectionsModule
import com.diploma.volodymyr.bicyclecity.dagger.module.RepositoryModule
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl.CreateGroupRideRoutePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GoogleMapsApiModule::class, GoogleMapsDirectionsModule::class, RepositoryModule::class])
interface CreateGroupRideComponent {
    fun inject(presenter: CreateGroupRideRoutePresenter)
}