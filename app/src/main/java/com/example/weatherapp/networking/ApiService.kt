package com.example.weatherapp.networking

import com.example.weatherapp.networking.responses.CurrentWeatherResponse
import com.example.weatherapp.networking.responses.WeatherListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/data/2.5/weather")
    fun getCurrentWeather(@Query("q") city: String): Call<CurrentWeatherResponse>


    @GET("/data/2.5/forecast")
    fun getWearherList(@Query("q") city: String): Call<WeatherListResponse>
}