package com.example.weatherapp.viewmodels.interfaces

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.Resource
import com.example.weatherapp.data.models.AverageDayWeatherDTO
import com.example.weatherapp.data.models.WeatherData
import com.example.weatherapp.networking.responses.CurrentWeatherResponse
import com.example.weatherapp.viewmodels.BaseViewModel

interface HomeViewModel : BaseViewModel {

    fun getCurrentWeather(): LiveData<Resource<CurrentWeatherResponse>>

    fun getDailyList(): LiveData<Resource<List<AverageDayWeatherDTO?>>>

    fun getTodayList(): LiveData<Resource<List<WeatherData>>>

    fun setCity(city: String): LiveData<String>
}