package com.example.weatherapp.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.weatherapp.app.Constants.DEFAULT_CITY_NAME
import com.example.weatherapp.app.Constants.ICON_BASE_URL
import com.example.weatherapp.app.iconEndpoint
import com.example.weatherapp.app.kelvToCels
import com.example.weatherapp.data.Resource
import com.example.weatherapp.networking.responses.CurrentWeatherResponse
import com.example.weatherapp.viewmodels.BaseViewModel
import com.example.weatherapp.viewmodels.interfaces.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.weatherIv
import android.annotation.SuppressLint
import androidx.appcompat.widget.SearchView
import com.example.weatherapp.R
import com.example.weatherapp.adapters.DailyAdapter
import com.example.weatherapp.adapters.TodayAdapter
import com.example.weatherapp.app.Constants.DEG_SYMBOL
import com.example.weatherapp.app.Constants.NO_VALUE
import com.example.weatherapp.app.Constants.PERCENT_SYMBOL
import com.example.weatherapp.app.KeyboardUtil
import com.example.weatherapp.data.models.AverageDayWeatherDTO
import com.example.weatherapp.data.models.WeatherData


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

    private var todayAdapter = TodayAdapter(emptyList<WeatherData>())
    private var dailyAdapter = DailyAdapter(emptyList<AverageDayWeatherDTO>())

    override fun setupLayout() {
        todayRV.adapter = todayAdapter
        dailyRV.adapter = dailyAdapter
    }

    override fun setupListeners() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    model?.setCity(it)
                    searchView.setQuery("", false)
                    KeyboardUtil.hideKeyboard(searchView)
//                    searchView.onActionViewCollapsed()
                }
                return true
            }

        })
    }

    override fun bindViewModel() {
        model = getViewModel()
        val city = arguments?.getString(CITY_NAME_KEY) ?: DEFAULT_CITY_NAME
        model?.setCity(city)?.observe(this, Observer<String> {
            cityTv.text = it
        })
        model?.getCurrentWeather()?.observe(this, Observer<Resource<CurrentWeatherResponse>> {
            when (it) {
                is Resource.Success -> {
                    hideProgress(currentPB, currentWeatherVG)
                    setupCurrentWeather(it.data!!)
                }
                is Resource.Error -> {
                    hideProgress(currentPB, currentWeatherVG)
                    showErrorDialog(it.message)
                }
                is Resource.Loading -> {
                    showProgress(currentPB, currentWeatherVG)
                }
            }
        })
        model?.getTodayList()?.observe(this, Observer<Resource<List<WeatherData>>> {
            when (it) {
                is Resource.Success -> {
                    hideProgress(detailedPB, detailedVG)
                    setupTodayWeather(it.data!!)
                }
                is Resource.Error -> {
                    hideProgress(detailedPB, detailedVG)
                    showErrorDialog(it.message)
                }
                is Resource.Loading -> {
                    showProgress(detailedPB, detailedVG)
                }
            }
        })
        model?.getDailyList()?.observe(this, Observer<Resource<List<AverageDayWeatherDTO?>>> {
            when (it) {
                is Resource.Success -> {
                    hideProgress(detailedPB, detailedVG)
                    setupDailyWeather(it.data!!)
                }
                is Resource.Error -> {
                    hideProgress(detailedPB, detailedVG)
                    showErrorDialog(it.message)
                }
                is Resource.Loading -> {
                    showProgress(detailedPB, detailedVG)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setupCurrentWeather(weatherResponse: CurrentWeatherResponse) {
        weatherResponse.weather?.get(0)?.icon?.let { icon ->
            Glide.with(this)
                .load(ICON_BASE_URL + icon.iconEndpoint())
                .into(weatherIv)
        } ?: run {
            weatherIv.setImageResource(R.drawable.red_cross)
        }
        weatherResponse.main?.let { main ->
            currentTempTv.text =
                if (main.temp?.kelvToCels() != null)
                    main.temp?.kelvToCels().toString() + DEG_SYMBOL.toChar()
                else
                    NO_VALUE
            minTempTv.text =
                if (main.tempMin?.kelvToCels() != null)
                    main.tempMin?.kelvToCels().toString() + DEG_SYMBOL.toChar()
                else
                    NO_VALUE
            maxTempTv.text =
                if (main.tempMax?.kelvToCels() != null)
                    main.tempMax?.kelvToCels().toString() + DEG_SYMBOL.toChar()
                else
                    NO_VALUE
            currHumidityTv.text =
                if (main.humidity != null)
                    main.humidity.toString() + PERCENT_SYMBOL
                else
                    NO_VALUE
            preasureTv.text =
                if (main.pressure != null)
                    main.pressure.toString()
                else
                    NO_VALUE
        }

    }

    private fun setupTodayWeather(list: List<WeatherData>) {
        todayAdapter.updateItemList(list)
    }

    private fun setupDailyWeather(list: List<AverageDayWeatherDTO?>) {
        dailyAdapter.updateItemList(list)
    }
}