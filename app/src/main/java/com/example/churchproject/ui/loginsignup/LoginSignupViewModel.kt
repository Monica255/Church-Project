package com.example.churchproject.ui.loginsignup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.churchproject.MyPreference
import com.example.churchproject.core.data.source.remote.model.RequestLogin
import com.example.churchproject.core.data.source.remote.model.RequestSignup
import com.example.churchproject.core.data.source.remote.model.UserData
import com.example.churchproject.core.data.source.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignupViewModel @Inject constructor(
    private val myPreference: MyPreference,
    private val authRepository: AuthRepository
):ViewModel() {
    var userData: UserData?=null

    fun login(data:RequestLogin) = authRepository.login(data).asLiveData()
    fun signup(data:RequestSignup) = authRepository.signup(data).asLiveData()
//    fun getLoginState(): LiveData<Boolean> {
//        return myPreference.getLoginState().asLiveData()
//    }
//
//    fun saveLoginState(loginState: Boolean) {
//        viewModelScope.launch {
//            myPreference.saveLoginState(loginState)
//        }
//    }

    fun getToken()=myPreference.getToken().asLiveData()


    fun saveToken(token: String) {
        viewModelScope.launch {
            myPreference.saveToken(token)
        }
    }


}