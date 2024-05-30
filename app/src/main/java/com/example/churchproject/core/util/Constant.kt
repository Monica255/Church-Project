package com.example.churchproject.core.util

import android.location.Location
import android.location.LocationManager

const val IP = "192.168.144.164"
const val EXTRA_DATE="date"
const val EXTRA_START_TIME="start_time"
const val EXTRA_END_TIME="end_time"
const val EXTRA_ID_EVENT="id_event"
const val EXTRA_NAME_EVENT="name_event"

//niels cafe
//val lokasiGereja = Location(LocationManager.GPS_PROVIDER).apply {
//    latitude = -5.152940355894553
//    longitude = 119.41489680005058
//}

//home
val lokasiGereja = Location(LocationManager.GPS_PROVIDER).apply {
latitude = -5.1693128741029515
    longitude = 119.40708469996024
}
