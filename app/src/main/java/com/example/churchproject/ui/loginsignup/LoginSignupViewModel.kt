package com.example.churchproject.ui.loginsignup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.churchproject.MyPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignupViewModel @Inject constructor(
    private val myPreference: MyPreference
):ViewModel() {
//    fun getLoginState(): LiveData<Boolean> {
//        return myPreference.getLoginState().asLiveData()
//    }
//
//    fun saveLoginState(loginState: Boolean) {
//        viewModelScope.launch {
//            myPreference.saveLoginState(loginState)
//        }
//    }

    fun getToken(): LiveData<String> {
        return myPreference.getToken().asLiveData()
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            myPreference.saveToken(token)
        }
    }


}