package com.example.yeogi.core.data.di

import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.data.usecase.GetAccommodationUseCase
import com.example.yeogi.core.data.usecase.GetAccommodationsUseCase
import com.example.yeogi.core.data.usecase.GetOverseaAccommodationsUseCase
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
        sharedRepository = sharedRepository
    )

    @Singleton
    @Provides
    fun provideGetOverseaAccommodationsUseCase(
        sharedRepository: SharedRepository
    ): GetOverseaAccommodationsUseCase = GetOverseaAccommodationsUseCase(
        sharedRepository = sharedRepository
    )

    @Singleton
    @Provides
    fun provideGetAccommodationUseCase(
        sharedRepository: SharedRepository
    ): GetAccommodationUseCase = GetAccommodationUseCase(
        sharedRepository = sharedRepository
    )
}