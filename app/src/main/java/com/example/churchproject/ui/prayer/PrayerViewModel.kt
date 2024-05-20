package com.example.churchproject.ui.prayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.churchproject.core.data.source.remote.model.RequestPrayer
import com.example.churchproject.core.data.source.repository.EventRepository
import com.example.churchproject.core.data.source.repository.PrayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrayerViewModel@Inject constructor(
    private val repository: PrayerRepository
): ViewModel(){
    fun addPrayer(data:RequestPrayer)=repository.addPrayer(data).asLiveData()

    fun getAllPrayer()=repository.getAllPrayes().asLiveData()

    fun deletePrayer(id:String)=repository.deletePrayer(id).asLiveData()
}