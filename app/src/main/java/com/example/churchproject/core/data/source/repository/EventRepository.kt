package com.example.churchproject.core.data.source.repository

import android.util.Log
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.Event
import com.example.churchproject.core.data.source.remote.model.RequestEvent
import com.example.churchproject.core.data.source.remote.model.RequestLogin
import com.example.churchproject.core.data.source.remote.model.RequestSignup
import com.example.churchproject.core.data.source.remote.model.ResponseAuth
import com.example.churchproject.core.data.source.remote.model.ResponseEvent
import com.example.churchproject.core.data.source.remote.network.ApiService
import com.example.churchproject.core.injection.CustomBaseUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EventRepository @Inject constructor(
    @CustomBaseUrl private val defaultApiService: ApiService
){

    fun getAllEvent(): Flow<Resource<List<Event>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.getAllEvent()
                emit(Resource.Success(response))
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun deleteEvent(id:String): Flow<Resource<ResponseEvent>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.deleteEvent(id)
                emit(Resource.Success(response))
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun addEvent(event:RequestEvent): Flow<Resource<ResponseEvent>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.addEvent(event)
                emit(Resource.Success(response))
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}