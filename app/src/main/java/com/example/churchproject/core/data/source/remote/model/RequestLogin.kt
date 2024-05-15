package com.example.churchproject.core.data.source.remote.model

data class RequestLogin(
    val email: String, val password: String
)

data class RequestSignup(
    val email: String, val name:String, val password: String
)


data class ResponseAuth(
    val status: String, val message: String, val user:UserData? =null
)

data class UserData(
    val name:String, val email:String, val role:String
)

data class ResponseUserData(
    val status: String, val message:String,val user:UserData
)