package com.example.churchproject.core.injection

import com.example.churchproject.core.data.source.remote.network.ApiService
import com.example.churchproject.core.util.IP
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    @Provides
    @DefaultBaseUrl
    fun provideDefaultApiService(client: OkHttpClient): ApiService {
        return createApiService(client, "https://beeble.vercel.app/")
    }
    @Provides
    @CustomBaseUrl
    fun provideCustomApiService(client: OkHttpClient): ApiService {
        return createApiService(client, "http://$IP/church/")
    }

    private fun createApiService(client: OkHttpClient, baseUrl: String): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}