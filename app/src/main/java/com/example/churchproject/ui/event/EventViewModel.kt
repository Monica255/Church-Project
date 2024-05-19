package com.example.churchproject.ui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.churchproject.core.data.source.remote.model.RequestEvent
import com.example.churchproject.core.data.source.repository.BibleRepository
import com.example.churchproject.core.data.source.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventViewModel@Inject constructor(
    private val event: EventRepository
): ViewModel() {

    fun getAllEvent() = event.getAllEvent().asLiveData()

    fun deleteEvent(id:String) = event.deleteEvent(id).asLiveData()
    fun addEvent(data:RequestEvent) = event.addEvent(data).asLiveData()
}