package com.diploma.volodymyr.bicyclecity.dagger.component

import com.akexorcist.googledirection.request.DirectionOriginRequest
import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsApiModule
import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsDirectionsModule
import com.diploma.volodymyr.bicyclecity.data.services.GoogleMapsApi
import dagger.Component

@Component(modules = [GoogleMapsApiModule::class, GoogleMapsDirectionsModule::class])
interface GoogleMapsComponent {
//    fun getGoogleMapsApi(): GoogleMapsApi
    fun getGoogleDirections(): DirectionOriginRequest
}