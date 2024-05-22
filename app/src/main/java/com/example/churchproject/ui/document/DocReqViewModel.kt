package com.example.churchproject.ui.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.churchproject.MyPreference
import com.example.churchproject.core.data.source.remote.model.RequestDoc
import com.example.churchproject.core.data.source.repository.AuthRepository
import com.example.churchproject.core.data.source.repository.DocReqRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DocReqViewModel @Inject constructor(
    private val docReqRepository: DocReqRepository
): ViewModel(){
    fun addDocReq(data:RequestDoc) =  docReqRepository.addDocReq(data).asLiveData()
    fun getAllDocReq(email:String)= docReqRepository.getAllDocReq(email).asLiveData()
    fun deleteDocReq(id:String)= docReqRepository.deleteDocReq(id).asLiveData()
}