package com.example.churchproject.core.data.source.repository

import android.util.Log
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.ResponseChapter
import com.example.churchproject.core.data.source.remote.model.ResponsePassage
import com.example.churchproject.core.data.source.remote.network.ApiService
import com.example.churchproject.core.injection.CustomBaseUrl
import com.example.churchproject.core.injection.DefaultBaseUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BibleRepository @Inject constructor(
    @DefaultBaseUrl private val defaultApiService: ApiService
){
    fun getPassage(): Flow<Resource<ResponsePassage>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.getList()
                emit(Resource.Success(response))
            } catch (e : Exception){
                Log.d("bible","error "+e.message.toString())
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getChapter(passage:String,verse:String): Flow<Resource<ResponseChapter>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.getChapter(passage,verse)
                emit(Resource.Success(response))
            } catch (e : Exception){
                Log.d("bible","error "+e.message.toString())
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}