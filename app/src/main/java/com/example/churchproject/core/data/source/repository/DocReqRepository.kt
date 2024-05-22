package com.example.churchproject.core.data.source.repository

import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.RequestDoc
import com.example.churchproject.core.data.source.remote.model.RequestPrayer
import com.example.churchproject.core.data.source.remote.model.ResponseDocReq
import com.example.churchproject.core.data.source.remote.model.ResponseListDocReq
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
class DocReqRepository @Inject constructor(
    @CustomBaseUrl private val defaultApiService: ApiService
) {
    fun getAllDocReq(email:String): Flow<Resource<ResponseListDocReq>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.getAllDocReq(email)
                if (response!=null){
                    emit(Resource.Success(response))
                }else{
                    emit(Resource.Error("Gagal mengambil data"))
                }
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun deleteDocReq(id:String): Flow<Resource<ResponseDocReq>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.deleteDocReq(id)
                if (response!=null){
                    emit(Resource.Success(response))
                }else{
                    emit(Resource.Error("Gagal menghapus data"))
                }
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun addDocReq(doc: RequestDoc): Flow<Resource<ResponseDocReq>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.addDocReq(doc)
                if (response!=null){
                    emit(Resource.Success(response))
                }else{
                    emit(Resource.Error("Gagal menambahkan data"))
                }
            } catch (e : Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}