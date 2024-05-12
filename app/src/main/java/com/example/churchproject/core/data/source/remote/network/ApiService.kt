package com.example.churchproject.core.data.source.remote.network

import com.example.churchproject.core.data.source.remote.model.ResponsePassage
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/v1/passage/list")
    suspend fun getList(): ResponsePassage
}