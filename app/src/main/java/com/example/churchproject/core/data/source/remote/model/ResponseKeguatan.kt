package com.example.churchproject.core.data.source.remote.model

import java.text.DateFormat



data class Event(
    val id_kegiatan:Int,
    val nama_kegiatan:String,
    val tanggal:String,
    val jam_mulai:String,
    val jam_berakhir:String
)