package com.example.churchproject.core.data.source.remote.network

import com.example.churchproject.core.injection.CustomBaseUrl
import com.example.churchproject.core.injection.DefaultBaseUrl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    @DefaultBaseUrl private val defaultApiService: ApiService,
    @CustomBaseUrl private val customApiService: ApiService
) {

}