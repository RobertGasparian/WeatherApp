package com.example.weatherapp.networking.responses

import com.example.weatherapp.data.models.City
import com.example.weatherapp.data.models.WeatherData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherListResponse {

    @SerializedName("cod")
    @Expose
    var cod: String? = null
    @SerializedName("message")
    @Expose
    var message: Double? = null
    @SerializedName("cnt")
    @Expose
    var cnt: Int? = null
    @SerializedName("list")
    @Expose
    var list: List<WeatherData>? = null
    @SerializedName("city")
    @Expose
    var city: City? = null
}
