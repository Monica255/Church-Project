package com.example.churchproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.ResponsePassage
import com.example.churchproject.core.data.source.repository.BibleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val bibleRepository: BibleRepository
): ViewModel() {
//    fun getPassage() = bibleRepository.getPassage().asLiveData()
}