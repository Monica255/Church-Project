package com.example.churchproject.core.data.source.remote.network

import com.example.churchproject.core.data.source.remote.model.ResponseChapter
import com.example.churchproject.core.data.source.remote.model.ResponsePassage
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("api/v1/passage/list")
    suspend fun getList(): ResponsePassage

    @GET("api/v1/passage/{abbr}/{chapter}?ver=tb")
    suspend fun getChapter(
        @Path("abbr") abbr: String,
        @Path("chapter") chapter: String,
    ): ResponseChapter
}