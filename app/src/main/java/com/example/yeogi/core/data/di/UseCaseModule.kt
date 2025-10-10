package com.example.yeogi.core.data.di

import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.data.usecase.GetAccommodationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideGetAccommodationsUseCase(
        sharedRepository: SharedRepository
    ): GetAccommodationsUseCase = GetAccommodationsUseCase(
        sharedRepository
    )
}