package com.example.weatherapp.data

import androidx.lifecycle.LiveData
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

    fun getWeatherList(city: String): LiveData<Resource<WeatherListResponse>> {
        return object : NetworkBoundResource<WeatherListResponse>() {
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
                                setValue(Resource.Success(it))
                            } ?: run {
                                setValue(Resource.Error("invalid data", null))
                            }
                        }

                    })
            }
        }.asLiveData()

    }
}