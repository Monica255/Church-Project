package com.example.churchproject.core.injection

import com.example.churchproject.core.data.source.remote.network.ApiService
import com.example.churchproject.core.data.source.repository.BibleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideBibleRepository(@DefaultBaseUrl defaultApiService: ApiService): BibleRepository{
        return BibleRepository(defaultApiService)
    }
}