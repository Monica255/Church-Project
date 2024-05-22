package com.example.churchproject.core.data.source.remote.model

data class RequestDoc (
    val user_email:String,
    val max_date:String,
    val doc_type:String
)

data class DocReq(
    val id:Int,
    val user_email:String,
    val max_date:String,
    val doc_type:String
)
data class ResponseDocReq(
    val status: String, val message:String
)

data class ResponseListDocReq(
    val status: String, val message:String, val data:List<DocReq>
)
