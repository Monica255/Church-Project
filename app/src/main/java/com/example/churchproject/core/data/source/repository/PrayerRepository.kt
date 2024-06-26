package com.example.churchproject.core.data.source.repository

import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.Event
import com.example.churchproject.core.data.source.remote.model.Prayer
import com.example.churchproject.core.data.source.remote.model.RequestEvent
import com.example.churchproject.core.data.source.remote.model.RequestPrayer
import com.example.churchproject.core.data.source.remote.model.ResponseEvent
import com.example.churchproject.core.data.source.remote.model.ResponseListPrayer
import com.example.churchproject.core.data.source.remote.model.ResponsePrayer
import com.example.churchproject.core.data.source.remote.network.ApiService
import com.example.churchproject.core.injection.CustomBaseUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrayerRepository @Inject constructor(
    @CustomBaseUrl private val defaultApiService: ApiService
){
    fun getAllPrayes(email:String): Flow<Resource<ResponseListPrayer>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.getAllPrayer(email)
                emit(Resource.Success(response))
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun deletePrayer(id:String): Flow<Resource<ResponsePrayer>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.deletePrayer(id)
                emit(Resource.Success(response))
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun addPrayer(prayer: RequestPrayer): Flow<Resource<ResponsePrayer>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.addPrayer(prayer)
                emit(Resource.Success(response))
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun editPrayer(prayerId: String, status:Int): Flow<Resource<ResponsePrayer>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.updateStatusPrayer(prayerId,status)
                emit(Resource.Success(response))
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}