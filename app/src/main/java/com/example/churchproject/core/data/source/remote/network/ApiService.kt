package com.example.churchproject.core.data.source.remote.network

import com.example.churchproject.core.data.source.remote.model.Attendance
import com.example.churchproject.core.data.source.remote.model.Event
import com.example.churchproject.core.data.source.remote.model.Prayer
import com.example.churchproject.core.data.source.remote.model.RequestAttendance
import com.example.churchproject.core.data.source.remote.model.RequestDoc
import com.example.churchproject.core.data.source.remote.model.RequestEvent
import com.example.churchproject.core.data.source.remote.model.RequestLogin
import com.example.churchproject.core.data.source.remote.model.RequestPrayer
import com.example.churchproject.core.data.source.remote.model.RequestSignup
import com.example.churchproject.core.data.source.remote.model.ResponseAttendance
import com.example.churchproject.core.data.source.remote.model.ResponseAuth
import com.example.churchproject.core.data.source.remote.model.ResponseChapter
import com.example.churchproject.core.data.source.remote.model.ResponseDocReq
import com.example.churchproject.core.data.source.remote.model.ResponseEvent
import com.example.churchproject.core.data.source.remote.model.ResponseListDocReq
import com.example.churchproject.core.data.source.remote.model.ResponseListPrayer
import com.example.churchproject.core.data.source.remote.model.ResponsePassage
import com.example.churchproject.core.data.source.remote.model.ResponsePrayer
import com.example.churchproject.core.data.source.remote.model.ResponseUserData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/v1/passage/list")
    suspend fun getList(): ResponsePassage

    @GET("api/v1/passage/{abbr}/{chapter}?ver=tb")
    suspend fun getChapter(
        @Path("abbr") abbr: String,
        @Path("chapter") chapter: String,
    ): ResponseChapter

    @POST("login.php")
    suspend fun login(
        @Body requestBody: RequestLogin
    ): ResponseAuth

    @POST("signup.php")
    suspend fun signup(
        @Body requestBody: RequestSignup
    ): ResponseAuth
    @POST("getAllEvent.php")
    suspend fun getAllEvent(): List<Event>

    @POST("getUserData.php")
    suspend fun getUserData(
        @Query("email") email:String): ResponseUserData

    @POST("addAttendance.php")
    suspend fun addAttendance(
        @Body requestBody: RequestAttendance): ResponseAttendance

    @POST("getAttendance.php")
    suspend fun getAttendance(
        @Query("email") email: String): List<Attendance>

    @POST("deleteAttendance.php")
    suspend fun deleteAttendance(
        @Query("id") id: String): ResponseAttendance

    @POST("deleteEvent.php")
    suspend fun deleteEvent(
        @Query("id") id: String): ResponseEvent

    @POST("addEvent.php")
    suspend fun addEvent(
        @Body requestBody: RequestEvent): ResponseEvent

    @POST("addPrayer.php")
    suspend fun addPrayer(
        @Body requestBody: RequestPrayer): ResponsePrayer

    @POST("getAllPrayer.php")
    suspend fun getAllPrayer(
        @Query("email") email:String
    ): ResponseListPrayer

    @POST("deletePrayer.php")
    suspend fun deletePrayer(
        @Query("id") id: String): ResponsePrayer

    @POST("addDocReq.php")
    suspend fun addDocReq(
        @Body requestBody: RequestDoc): ResponseDocReq

    @POST("getAllDocReq.php")
    suspend fun getAllDocReq(
        @Query("email") email:String
    ): ResponseListDocReq

    @POST("deleteDocReq.php")
    suspend fun deleteDocReq(
        @Query("id") id: String): ResponseDocReq
}