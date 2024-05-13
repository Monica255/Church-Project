package com.example.churchproject.ui.bible

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.churchproject.core.data.source.remote.model.Passage
import com.example.churchproject.core.data.source.repository.BibleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BibleViewModel@Inject constructor(
    private val bibleRepository: BibleRepository
): ViewModel() {

    var selectedPassage = MutableLiveData<Passage?>()
    var passages= MutableLiveData<List<Passage>>()
    fun getPassage() = bibleRepository.getPassage().asLiveData()

    fun getChapter(passage:String,verse:String) = bibleRepository.getChapter(passage,verse).asLiveData()
}