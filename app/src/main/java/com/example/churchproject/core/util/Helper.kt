package com.example.churchproject.core.util

import android.location.Location
import android.util.Log
import com.example.churchproject.core.data.source.remote.model.Date
import java.text.SimpleDateFormat
import java.util.Locale


object Helper {
    fun generateNumberList(count: Int): List<Int> {
        return (1..count).toList()
    }

    fun getMonthName(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "Mei"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Ags"
            9 -> "Sep"
            10 -> "Okt"
            11 -> "Nov"
            12 -> "Des"
            else -> "-"
        }
    }

    fun getDate(date:String): Date {
        val dates = date.split("-")
        return Date(dates[0], getMonthName(dates[1].toInt()),dates[2])
    }

    fun getEmail(token:String):String{
        return token.split("#")[0]
    }
    fun getRole(token:String):String{
        return token.split("#")[1]
    }

    fun formatDateAttendance(inputDateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())

        return try {
            val date = inputFormat.parse(inputDateString)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun checkRadius(myLocation: Location, radius: Float = 500.0f): Boolean {
        Log.d("mlocation", lokasiGereja.toString() )
        Log.d("mlocation",myLocation.toString())
        // Menghitung jarak antara dua lokasi dalam meter
        val distance = myLocation.distanceTo(lokasiGereja)

        // Membandingkan jarak dengan radius yang diberikan
        return distance <= radius
    }

}