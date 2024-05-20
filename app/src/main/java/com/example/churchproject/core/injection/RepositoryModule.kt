package com.example.churchproject.core.injection

import com.example.churchproject.core.data.source.remote.network.ApiService
import com.example.churchproject.core.data.source.repository.AttendanceRepository
import com.example.churchproject.core.data.source.repository.AuthRepository
import com.example.churchproject.core.data.source.repository.BibleRepository
import com.example.churchproject.core.data.source.repository.EventRepository
import com.example.churchproject.core.data.source.repository.PrayerRepository
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

    @Provides
    @Singleton
    fun provideAuthRepository(@CustomBaseUrl customApiService: ApiService): AuthRepository{
        return AuthRepository(customApiService)
    }

    @Provides
    @Singleton
    fun provideEventRepository(@CustomBaseUrl customApiService: ApiService): EventRepository{
        return EventRepository(customApiService)
    }

    @Provides
    @Singleton
    fun provideAttendanceRepository(@CustomBaseUrl customApiService: ApiService): AttendanceRepository{
        return AttendanceRepository(customApiService)
    }

    @Provides
    @Singleton
    fun providePrayerRepository(@CustomBaseUrl customApiService: ApiService): PrayerRepository{
        return PrayerRepository(customApiService)
    }
}