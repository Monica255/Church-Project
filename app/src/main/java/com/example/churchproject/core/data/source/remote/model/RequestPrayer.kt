package com.example.churchproject.core.data.source.remote.model

data class RequestPrayer(
    val user_email:String,
    val prayer:String
)

data class Prayer(
    val id:Int,
    val user_email:String,
    val timestamp:String,
    val prayer:String,
    val status: Int = 0
)
data class ResponsePrayer(
    val status: String, val message:String
)

data class ResponseListPrayer(
    val status: String, val message:String, val data:List<Prayer>
)

