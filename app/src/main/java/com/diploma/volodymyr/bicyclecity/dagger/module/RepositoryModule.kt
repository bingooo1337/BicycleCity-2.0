package com.diploma.volodymyr.bicyclecity.dagger.module

import com.diploma.volodymyr.bicyclecity.data.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module(includes = [(FirebaseModule::class)])
class RepositoryModule {

    @Provides
    fun provideUserRepositoryImpl(db: FirebaseFirestore, auth: FirebaseAuth) =
            UserRepositoryImpl(db, auth)
}