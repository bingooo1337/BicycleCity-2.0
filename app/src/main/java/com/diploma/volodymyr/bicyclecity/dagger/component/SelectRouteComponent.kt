package com.diploma.volodymyr.bicyclecity.dagger.component

import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsDirectionsModule
import com.diploma.volodymyr.bicyclecity.dagger.module.RepositoryModule
import com.diploma.volodymyr.bicyclecity.presentation.presenter.impl.SelectRoutePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GoogleMapsDirectionsModule::class, RepositoryModule::class])
interface SelectRouteComponent {
    fun inject(presenter: SelectRoutePresenter)
}