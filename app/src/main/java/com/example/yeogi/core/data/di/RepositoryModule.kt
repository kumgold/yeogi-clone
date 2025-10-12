package com.example.yeogi.core.data.di

import android.content.Context
import com.example.yeogi.core.data.repository.RecentSearchRepository
import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.feature.home.data.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideSharedRepository(): SharedRepository = SharedRepository()

    @Singleton
    @Provides
    fun provideRecentSearchRepository(
        @ApplicationContext context: Context
    ): RecentSearchRepository = RecentSearchRepository(context)

    @Singleton
    @Provides
    fun provideHomeRepository(): HomeRepository = HomeRepository()
}