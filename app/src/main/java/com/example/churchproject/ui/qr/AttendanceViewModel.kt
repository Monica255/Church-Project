package com.example.churchproject.ui.qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.churchproject.MyPreference
import com.example.churchproject.core.data.source.remote.model.RequestAttendance
import com.example.churchproject.core.data.source.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel@Inject constructor(
    private val attendanceRepository: AttendanceRepository,
    private val myPreference: MyPreference
): ViewModel() {
    fun addAttendance(data: RequestAttendance) = attendanceRepository.addAttendance(data).asLiveData()
    fun getToken() = myPreference.getToken().asLiveData()

    fun getAttendance(email: String) = attendanceRepository.getAttendance(email).asLiveData()

    fun deleteAttendance(id: String) = attendanceRepository.deleteAttendance(id).asLiveData()
}