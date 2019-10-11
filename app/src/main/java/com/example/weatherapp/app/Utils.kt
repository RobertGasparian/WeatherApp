package com.example.weatherapp.app

import android.content.Context
import android.location.Geocoder
import android.location.Location
import java.util.*

fun String.iconEndpoint(): String {
    return "$this@2x.png"
}

fun Location.getCity(context: Context): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    val addresses = geoCoder.getFromLocation(this.latitude, this.longitude, 1)
    return addresses[0].getAddressLine(0)
}