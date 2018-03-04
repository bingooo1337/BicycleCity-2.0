package com.diploma.volodymyr.bicyclecity

import android.app.Application
import com.diploma.volodymyr.bicyclecity.dagger.component.DaggerGoogleMapsComponent
import com.diploma.volodymyr.bicyclecity.dagger.component.GoogleMapsComponent
import com.diploma.volodymyr.bicyclecity.dagger.module.AppModule
import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsApiModule

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    init {
        instance = this
    }

    private lateinit var googleMapsComponent: GoogleMapsComponent

    override fun onCreate() {
        super.onCreate()
        googleMapsComponent = DaggerGoogleMapsComponent.builder()
                .googleMapsApiModule(GoogleMapsApiModule())
                .build()
    }

    fun getGoogleMapsComponent() = googleMapsComponent
}