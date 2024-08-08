package com.ahmadsodik.sarpras.di

import com.ahmadsodik.sarpras.data.repository.barang.BarangRepository
import com.ahmadsodik.sarpras.data.repository.barang.BarangRepositoryImpl
import com.ahmadsodik.sarpras.data.repository.pinjaman.PinjamRepository
import com.ahmadsodik.sarpras.data.repository.pinjaman.PinjamRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideBarangRepository(barangRepositoryImpl: BarangRepositoryImpl) : BarangRepository

    @Binds
    @Singleton
    abstract fun providePinjamRepository(pinjamRepositoryImpl: PinjamRepositoryImpl) : PinjamRepository
}