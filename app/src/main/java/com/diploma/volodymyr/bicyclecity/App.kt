package com.diploma.volodymyr.bicyclecity

import android.app.Application
import com.diploma.volodymyr.bicyclecity.dagger.component.DaggerDataComponent
import com.diploma.volodymyr.bicyclecity.dagger.component.DaggerGoogleMapsComponent
import com.diploma.volodymyr.bicyclecity.dagger.component.DataComponent
import com.diploma.volodymyr.bicyclecity.dagger.component.GoogleMapsComponent
import com.diploma.volodymyr.bicyclecity.dagger.module.GoogleMapsDirectionsModule
import com.diploma.volodymyr.bicyclecity.dagger.module.RepositoryModule

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    init {
        instance = this
    }

    private lateinit var googleMapsComponent: GoogleMapsComponent
    private lateinit var dataComponent: DataComponent

    override fun onCreate() {
        super.onCreate()
        googleMapsComponent = DaggerGoogleMapsComponent.builder()
                .googleMapsDirectionsModule(GoogleMapsDirectionsModule())
                .build()
        dataComponent = DaggerDataComponent.builder()
                .repositoryModule(RepositoryModule())
                .build()
    }

    fun getGoogleMapsComponent() = googleMapsComponent
    fun getDataComponent() = dataComponent
}