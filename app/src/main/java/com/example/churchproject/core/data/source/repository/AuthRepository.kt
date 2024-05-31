package com.example.churchproject.core.data.source.repository

import android.util.Log
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.RequestLogin
import com.example.churchproject.core.data.source.remote.model.RequestSignup
import com.example.churchproject.core.data.source.remote.model.ResponseAuth
import com.example.churchproject.core.data.source.remote.model.ResponseChapter
import com.example.churchproject.core.data.source.remote.model.ResponsePassage
import com.example.churchproject.core.data.source.remote.model.ResponseUserData
import com.example.churchproject.core.data.source.remote.network.ApiService
import com.example.churchproject.core.injection.CustomBaseUrl
import com.example.churchproject.core.injection.DefaultBaseUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    @CustomBaseUrl private val defaultApiService: ApiService
){

    fun login(data:RequestLogin): Flow<Resource<ResponseAuth>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.login(data)
                emit(Resource.Success(response))
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun signup(data:RequestSignup): Flow<Resource<ResponseAuth>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.signup(data)
                emit(Resource.Success(response))
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getUserData(email:String): Flow<Resource<ResponseUserData>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.getUserData(email)
                emit(Resource.Success(response))
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}