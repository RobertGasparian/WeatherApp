package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.Repository
import com.example.weatherapp.networking.NetworkManager

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Repository.init(NetworkManager.apiService)
    }
}