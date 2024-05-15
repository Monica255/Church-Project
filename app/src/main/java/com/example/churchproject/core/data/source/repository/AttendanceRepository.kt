package com.example.churchproject.core.data.source.repository

import android.util.Log
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.Attendance
import com.example.churchproject.core.data.source.remote.model.Event
import com.example.churchproject.core.data.source.remote.model.RequestAttendance
import com.example.churchproject.core.data.source.remote.model.ResponseAttendance
import com.example.churchproject.core.data.source.remote.network.ApiService
import com.example.churchproject.core.injection.CustomBaseUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepository @Inject constructor(
    @CustomBaseUrl private val defaultApiService: ApiService
){
    fun addAttendance(data:RequestAttendance): Flow<Resource<ResponseAttendance>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.addAttendance(data)
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

    fun getAttendance(data:String): Flow<Resource<List<Attendance>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.getAttendance(data)
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

    fun deleteAttendance(data:String): Flow<Resource<ResponseAttendance>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = defaultApiService.deleteAttendance(data)
                Log.d("delete",response.toString())
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

}