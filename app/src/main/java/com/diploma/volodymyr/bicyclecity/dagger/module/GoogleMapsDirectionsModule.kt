package com.diploma.volodymyr.bicyclecity.dagger.module

import com.akexorcist.googledirection.GoogleDirection
import com.diploma.volodymyr.bicyclecity.App
import com.diploma.volodymyr.bicyclecity.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class GoogleMapsDirectionsModule {
    @Provides
    fun googleDirections(application: App) = GoogleDirection
            .withServerKey(application.getString(R.string.geo_api_server_key))
}