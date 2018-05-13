package com.diploma.volodymyr.bicyclecity.dagger.module

import com.diploma.volodymyr.bicyclecity.data.repository.GroupRideRepositoryImpl
import com.diploma.volodymyr.bicyclecity.data.repository.MarkerRepositoryImpl
import com.diploma.volodymyr.bicyclecity.data.repository.UserRepositoryImpl
import com.diploma.volodymyr.bicyclecity.model.GroupRideRepository
import com.diploma.volodymyr.bicyclecity.model.MarkerRepository
import com.diploma.volodymyr.bicyclecity.model.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(FirebaseModule::class)])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepositoryImpl(db: FirebaseFirestore, auth: FirebaseAuth): UserRepository =
            UserRepositoryImpl(db, auth)

    @Provides
    @Singleton
    fun provideMarkerRepositoryImpl(db: FirebaseFirestore): MarkerRepository =
            MarkerRepositoryImpl(db)

    @Provides
    @Singleton
    fun provideGroupRideRepositoryImpl(db: FirebaseFirestore): GroupRideRepository =
            GroupRideRepositoryImpl(db)
}