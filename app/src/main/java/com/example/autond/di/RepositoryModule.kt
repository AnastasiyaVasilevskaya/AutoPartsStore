package com.example.autond.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideCarRepository(carApiService: CarApiService): CarRepository {
        return CarRepositoryImpl(carApiService)
    }
}