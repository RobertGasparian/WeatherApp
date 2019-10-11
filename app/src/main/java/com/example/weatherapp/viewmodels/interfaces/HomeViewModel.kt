package com.example.weatherapp.viewmodels.interfaces

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.Resource
import com.example.weatherapp.networking.responses.CurrentWeatherResponse
import com.example.weatherapp.networking.responses.WeatherListResponse
import com.example.weatherapp.viewmodels.BaseViewModel

interface HomeViewModel : BaseViewModel {

    fun getCurrentWeather(city: String): LiveData<Resource<CurrentWeatherResponse>>

    fun getWeatherList(city: String): LiveData<Resource<WeatherListResponse>>
}