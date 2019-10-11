package com.example.weatherapp.viewmodels.implementations

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.weatherapp.data.Repository
import com.example.weatherapp.data.Resource
import com.example.weatherapp.networking.responses.CurrentWeatherResponse
import com.example.weatherapp.networking.responses.WeatherListResponse
import com.example.weatherapp.viewmodels.BaseViewModelImpl
import com.example.weatherapp.viewmodels.interfaces.HomeViewModel

class HomeViewModelImpl(application: Application) : BaseViewModelImpl(application), HomeViewModel {

    private lateinit var currentWeatherLD: LiveData<Resource<CurrentWeatherResponse>>

    private lateinit var weatherListLD: LiveData<Resource<WeatherListResponse>>

    override fun getCurrentWeather(city: String): LiveData<Resource<CurrentWeatherResponse>> {
        currentWeatherLD = Repository.getCurrentWeather(city)
        return currentWeatherLD
    }

    override fun getWeatherList(city: String): LiveData<Resource<WeatherListResponse>> {
        weatherListLD = Repository.getWeatherList(city)
        return weatherListLD
    }

}