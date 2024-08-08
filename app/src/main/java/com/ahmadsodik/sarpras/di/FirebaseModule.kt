package com.ahmadsodik.sarpras.di

import com.ahmadsodik.sarpras.data.source.firebase.FirebaseService
import com.ahmadsodik.sarpras.data.source.firebase.FirebaseServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {

    @Binds
    @Singleton
    abstract fun provideFirebaseService(firebaseServiceImpl: FirebaseServiceImpl) : FirebaseService
}