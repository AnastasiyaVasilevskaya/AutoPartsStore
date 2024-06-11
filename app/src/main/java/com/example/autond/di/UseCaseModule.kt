package com.example.autond.di

import com.example.autond.domain.repository.SessionRepository
import com.example.autond.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideLoginUseCase(repository: SessionRepository): LoginUseCase {
        return LoginUseCase(repository)
    }
}