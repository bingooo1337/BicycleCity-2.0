package com.diploma.volodymyr.bicyclecity.common

import android.app.Application
import com.diploma.volodymyr.bicyclecity.dagger.component.*
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
    private lateinit var selectRouteComponent: SelectRouteComponent

    override fun onCreate() {
        super.onCreate()
        googleMapsComponent = DaggerGoogleMapsComponent.builder()
                .googleMapsDirectionsModule(GoogleMapsDirectionsModule())
                .build()
        dataComponent = DaggerDataComponent.builder()
                .repositoryModule(RepositoryModule())
                .build()
        selectRouteComponent = DaggerSelectRouteComponent.builder()
                .repositoryModule(RepositoryModule())
                .googleMapsDirectionsModule(GoogleMapsDirectionsModule())
                .build()
    }

    fun getGoogleMapsComponent() = googleMapsComponent
    fun getDataComponent() = dataComponent
    fun getSelectRouteComponent() = selectRouteComponent
}