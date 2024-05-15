package com.example.churchproject.core.data.source.remote.model

import java.sql.Timestamp

class RequestAttendance (
    val email_user:String,
    val id_event:String
)

data class ResponseAttendance(
    val status: String, val message:String
)

data class Attendance(
    val id:Int,
    val timestamp: String,
    val id_event: String,
    val name_event:String,
    val email_user: String
)