package com.example.weatherapp.app

import android.content.Context
import android.location.Geocoder
import android.location.Location
import java.util.*
import kotlin.collections.Map.Entry
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.math.BigDecimal
import java.math.RoundingMode


fun String.iconEndpoint(): String {
    return "$this@2x.png"
}

fun Location.getCity(context: Context): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    val addresses = geoCoder.getFromLocation(this.latitude, this.longitude, 1)
    return addresses[0].locality
}

fun Double.kelvToCels(): Double {
    return (this - 273.15).round()
}

fun Double.celsToKelv(): Double {
    return (this + 273.15).round()
}

fun Double.round(count: Int = 2): Double {
    require(count >= 0)
    var bd = BigDecimal.valueOf(this)
    bd = bd.setScale(count, RoundingMode.HALF_UP)
    return bd.toDouble()
}

fun <T> List<T>.getMostContainedItem(): T? {
    if (this.isEmpty()) return null
    val map = hashMapOf<T, Int>()
    this.forEach {
        val count = map[it]
        val newCount = if (count == null) 1 else count + 1
        map[it] = newCount
    }

    var max: Entry<T, Int>? = null

    for (entry in map.entries) {
        if (max == null || entry.value > max.value)
            max = entry
    }
    return max?.key
}

fun Long.getTime(): String {
    val date = Date(this * 1000)
    val calendar = Calendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone("UTC")
        time = date
    }
    val time = calendar.get(Calendar.HOUR_OF_DAY)
    return "${time}h"
}