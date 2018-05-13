package com.diploma.volodymyr.bicyclecity.dagger.component

import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsApiModule
import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsDirectionsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GoogleMapsApiModule::class, GoogleMapsDirectionsModule::class])
interface GoogleMapsComponent {
}