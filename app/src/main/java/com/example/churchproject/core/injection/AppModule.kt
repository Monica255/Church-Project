package com.example.churchproject.core.injection

import android.content.Context
import com.example.churchproject.MyPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun dataStoreManager(@ApplicationContext appContext: Context): MyPreference =
        MyPreference(appContext)
}