package com.example.churchproject.core.data.source.remote.model


data class Event(
    val id_kegiatan:Int,
    val nama_kegiatan:String,
    val tanggal:String,
    val jam_mulai:String,
    val jam_berakhir:String
)

data class ResponseEvent(
    val status: String, val message:String
)

data class RequestEvent(
    val nama_kegiatan:String,
    val tanggal:String,
    val jam_mulai:String,
    val jam_berakhir:String
)