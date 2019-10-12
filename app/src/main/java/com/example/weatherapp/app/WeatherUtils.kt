package com.example.weatherapp.app

import android.util.Log
import com.example.weatherapp.data.models.AverageDayWeatherDTO
import com.example.weatherapp.data.models.WeatherData
import com.example.weatherapp.networking.responses.WeatherListResponse
import java.util.*

object WeatherUtils {

    fun filterWeather(weatherListResponse: WeatherListResponse): List<List<WeatherData>>? {
        val list = weatherListResponse.list
        list?.isNotEmpty() ?: return null
        val calendar = Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val dayList = mutableListOf<List<WeatherData>>()
        var currentList = mutableListOf<WeatherData>()
        var currentDate: Int? = null
        for (i in list.indices) {
            val item = list[i]
            item.dt?.let {
                calendar.time = Date(it * 1000)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                when {
                    currentDate == null -> {
                        if (currentDate == null) {
                            currentDate = day
                        }
                        currentList.add(item)
                    }
                    currentDate != day -> {
                        currentDate = day
                        dayList.add(currentList)
                        currentList = mutableListOf()
                        currentList.add(item)
                    }
                    else -> {
                        currentList.add(item)
                    }
                }
                if (i == list.size - 1) {
                    dayList.add(currentList)
                }
            } ?: run {
                Log.e("date_log", "no date")
            }
        }
        return dayList
    }

    fun getAverageWeatherData(list: List<WeatherData>): AverageDayWeatherDTO? {
        if (list.isEmpty()) return null
        var sum = 0.0
        val calendar = Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("UTC")
            val dt = list[0].dt!! * 1000    //todo. need to handle nullability
            time = Date(dt)
        }
        val date = "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}"
        val weathers = mutableListOf<String>()
        list.forEach { item ->
            sum += (item.main?.temp ?: 0.0)
            val weatherList = item.weather ?: emptyList()
            weatherList.forEach { weather ->
                weather.icon?.let {
                    weathers.add(it)
                }
            }
        }
        return AverageDayWeatherDTO(sum / list.size, date, weathers.getMostContainedItem())
    }
}