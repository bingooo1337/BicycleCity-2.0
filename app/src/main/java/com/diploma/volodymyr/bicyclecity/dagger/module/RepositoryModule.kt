package com.diploma.volodymyr.bicyclecity.dagger.module

import com.diploma.volodymyr.bicyclecity.data.repository.*
import com.diploma.volodymyr.bicyclecity.model.*
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

    @Provides
    @Singleton
    fun provideFeedRepositoryImpl(db: FirebaseFirestore): FeedRepository =
            FeedRepositoryImpl(db)

    @Provides
    @Singleton
    fun provideCompetitionRepositoryImpl(db: FirebaseFirestore): CompetitionRepository =
            CompetitionRepositoryImpl(db)
}