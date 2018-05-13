package com.diploma.volodymyr.bicyclecity.dagger.module

import com.diploma.volodymyr.bicyclecity.data.services.GoogleMapsApi
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [(OkHttpClientModule::class)])
class GoogleMapsApiModule {

    @Provides
    @Singleton
    fun googleMapsApi(retrofit: Retrofit): GoogleMapsApi =
            retrofit.create(GoogleMapsApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
            gsonConverterFactory: GsonConverterFactory,
            okHttpClient: OkHttpClient,
            rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @Provides
    @Singleton
    fun rxJava2CallAdapterFactory(): RxJava2CallAdapterFactory =
            RxJava2CallAdapterFactory.create()
}