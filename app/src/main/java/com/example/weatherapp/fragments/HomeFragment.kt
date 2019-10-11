package com.example.weatherapp.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.app.Constants.DEFAULT_CITY_NAME
import com.example.weatherapp.data.Resource
import com.example.weatherapp.networking.responses.CurrentWeatherResponse
import com.example.weatherapp.networking.responses.WeatherListResponse
import com.example.weatherapp.viewmodels.BaseViewModel
import com.example.weatherapp.viewmodels.interfaces.HomeViewModel

class HomeFragment : BaseFragment() {

    companion object {

        private const val CITY_NAME_KEY = "city_name_key"

        fun newInstance(cityName: String?): HomeFragment {
            return HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(CITY_NAME_KEY, cityName)
                }
            }
        }
    }

    override val resourceId: Int
        get() = R.layout.fragment_home

    private var model: HomeViewModel? = null

    override val baseViewModel: BaseViewModel?
        get() = model

    override fun setupLayout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setupClicks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bindViewModel() {
        model = getViewModel(HomeViewModel::class.java)
        val city = arguments?.getString(CITY_NAME_KEY) ?: DEFAULT_CITY_NAME
        model?.getCurrentWeather(city)?.observe(this, Observer<Resource<CurrentWeatherResponse>> {
            when (it) {
                is Resource.Success -> {

                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        })
        model?.getWeatherList(city)?.observe(this, Observer<Resource<WeatherListResponse>> {
            when (it) {
                is Resource.Success -> {

                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        })
    }
}