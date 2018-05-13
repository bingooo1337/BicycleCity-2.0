package com.diploma.volodymyr.bicyclecity.dagger.module

import com.diploma.volodymyr.bicyclecity.common.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication() = App.instance
}