package com.example.weatherapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.app.WeatherUtils
import com.example.weatherapp.data.models.AverageDayWeatherDTO
import com.example.weatherapp.data.models.WeatherData
import com.example.weatherapp.networking.ApiService
import com.example.weatherapp.networking.responses.CurrentWeatherResponse
import com.example.weatherapp.networking.responses.WeatherListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    private lateinit var apiService: ApiService

    fun init(apiService: ApiService) {
        this.apiService = apiService
    }

    fun getCurrentWeather(city: String): LiveData<Resource<CurrentWeatherResponse>> {
        return object : NetworkBoundResource<CurrentWeatherResponse>() {
            override fun createCall() {
                apiService.getCurrentWeather(city)
                    .enqueue(object : Callback<CurrentWeatherResponse> {
                        override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                            setValue(Resource.Error(t.message, null))
                        }

                        override fun onResponse(
                            call: Call<CurrentWeatherResponse>,
                            response: Response<CurrentWeatherResponse>
                        ) {
                            response.body()?.let {
                                setValue(Resource.Success(it))
                            } ?: run {
                                setValue(Resource.Error("invalid data", null))
                            }
                        }

                    })
            }
        }.asLiveData()

    }

    fun getWeatherList(city: String): LiveData<Resource<List<List<WeatherData>>>> {
        return object : NetworkBoundResource<List<List<WeatherData>>>() {
            override fun createCall() {
                apiService.getWearherList(city)
                    .enqueue(object : Callback<WeatherListResponse> {
                        override fun onFailure(call: Call<WeatherListResponse>, t: Throwable) {
                            setValue(Resource.Error(t.message, null))
                        }

                        override fun onResponse(
                            call: Call<WeatherListResponse>,
                            response: Response<WeatherListResponse>
                        ) {
                            response.body()?.let {
                                val list = WeatherUtils.filterWeather(it)
                                list?.let { weatherList ->
                                    setValue(Resource.Success(weatherList))
                                } ?: run {
                                    setValue(Resource.Error("invalid data", null))
                                }
                            } ?: run {
                                setValue(Resource.Error("invalid data", null))
                            }
                        }

                    })
            }
        }.asLiveData()
    }

    fun getAvarageDailyWeather(listOfList: List<List<WeatherData>>): LiveData<Resource<List<AverageDayWeatherDTO?>>> {
        val liveData = MutableLiveData<Resource<List<AverageDayWeatherDTO?>>>()
        val list = mutableListOf<AverageDayWeatherDTO?>()
        listOfList.forEach {
            list.add(WeatherUtils.getAverageWeatherData(it))
        }
        return if (list.isEmpty()) {
            liveData.value = Resource.Error("empty list of data", list)
            liveData
        } else {
            liveData.value = Resource.Success(list)
            liveData
        }
    }
}