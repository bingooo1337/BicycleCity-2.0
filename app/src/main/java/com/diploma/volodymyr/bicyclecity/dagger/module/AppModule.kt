package com.diploma.volodymyr.bicyclecity.dagger.module

import com.diploma.volodymyr.bicyclecity.App
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideApplication() = App.instance
}