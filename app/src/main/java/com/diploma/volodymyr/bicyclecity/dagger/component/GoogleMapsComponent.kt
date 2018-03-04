package com.diploma.volodymyr.bicyclecity.dagger.component

import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsApiModule
import com.diploma.volodymyr.bicyclecity.data.services.GoogleMapsApi
import dagger.Component

@Component(modules = [(GoogleMapsApiModule::class)])
interface GoogleMapsComponent {
    fun getGoogleMapsApi(): GoogleMapsApi
}