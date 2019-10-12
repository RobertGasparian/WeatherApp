package com.example.weatherapp.viewmodels.implementations

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.weatherapp.data.Repository
import com.example.weatherapp.data.Resource
import com.example.weatherapp.data.models.AverageDayWeatherDTO
import com.example.weatherapp.data.models.WeatherData
import com.example.weatherapp.networking.responses.CurrentWeatherResponse
import com.example.weatherapp.viewmodels.BaseViewModelImpl
import com.example.weatherapp.viewmodels.interfaces.HomeViewModel
import java.lang.RuntimeException

class HomeViewModelImpl(application: Application) : BaseViewModelImpl(application), HomeViewModel {


    private var cityLD = MutableLiveData<String>()

    private var currentWeatherLD: LiveData<Resource<CurrentWeatherResponse>> = Transformations
        .switchMap(cityLD) { city ->
            return@switchMap Repository.getCurrentWeather(city)
        }

    private var weatherListLD: LiveData<Resource<List<List<WeatherData>>>> = Transformations
        .switchMap(cityLD) { city ->
            return@switchMap Repository.getWeatherList(city)
        }

    private var dailyDataListLD: LiveData<Resource<List<AverageDayWeatherDTO?>>> = Transformations
        .switchMap(weatherListLD) {
           return@switchMap when (it) {
               is Resource.Success -> {
                   val dailyList = it.data!!.toMutableList().drop(1)
                   Repository.getAvarageDailyWeather(dailyList)
               }
               is Resource.Error -> MutableLiveData<Resource<List<AverageDayWeatherDTO?>>>().apply {
                   value = Resource.Error(it.message)
               }
               is Resource.Loading -> MutableLiveData<Resource<List<AverageDayWeatherDTO?>>>().apply {
                   value = Resource.Loading()
               }
           }
        }

    private var todayWeatherList: LiveData<Resource<List<WeatherData>>> = Transformations.switchMap(weatherListLD) {
        return@switchMap when (it) {
            is Resource.Success -> {
                MutableLiveData<Resource<List<WeatherData>>>().apply {
                    it.data?.get(0)?.let { todayList ->
                        value = Resource.Success(todayList)
                    } ?: run {
                        value = Resource.Error("list is empty")
                    }
                }
            }
            is Resource.Error -> MutableLiveData<Resource<List<WeatherData>>>().apply {
                value = Resource.Error(it.message)
            }
            is Resource.Loading -> MutableLiveData<Resource<List<WeatherData>>>().apply {
                value = Resource.Loading()
            }
        }
    }

    override fun getCurrentWeather(): LiveData<Resource<CurrentWeatherResponse>> {
        if (cityLD.value == null) throw RuntimeException("City must be initialised")
        return currentWeatherLD
    }

    override fun getDailyList(): LiveData<Resource<List<AverageDayWeatherDTO?>>> {
        if (cityLD.value == null) throw RuntimeException("City must be initialised")
        return dailyDataListLD
    }

    override fun getTodayList(): LiveData<Resource<List<WeatherData>>> {
        if (cityLD.value == null) throw RuntimeException("City must be initialised")
        return todayWeatherList
    }

    override fun setCity(city: String): LiveData<String> {
        cityLD.value = city //todo. need to move to constructor (extend ViewModelsFactory etc. etc.)
        return cityLD
    }
}