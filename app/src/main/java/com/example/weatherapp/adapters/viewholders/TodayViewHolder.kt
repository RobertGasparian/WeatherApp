package com.example.weatherapp.adapters.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.app.Constants.DEG_SYMBOL
import com.example.weatherapp.app.Constants.ICON_BASE_URL
import com.example.weatherapp.app.Constants.NO_VALUE
import com.example.weatherapp.app.getMostContainedItem
import com.example.weatherapp.app.getTime
import com.example.weatherapp.app.iconEndpoint
import com.example.weatherapp.app.kelvToCels
import com.example.weatherapp.data.models.WeatherData
import kotlinx.android.synthetic.main.item_today.*
import kotlinx.android.synthetic.main.item_today.tempTv
import kotlinx.android.synthetic.main.item_today.weatherIv

class TodayViewHolder(view: View) : BaseViewHolder<WeatherData>(view) {

    override val containerView: View?
        get() = view

    override fun bind(item: WeatherData?) {
        if (item == null) {
            weatherIv.setImageResource(R.drawable.red_cross)
            timeTv.text = NO_VALUE
            tempTv.text = NO_VALUE
            return
        }
        val weather = item.weather?.getMostContainedItem()
        weather?.icon?.let { icon ->
            Glide.with(itemView.context)
                .load(ICON_BASE_URL + icon.iconEndpoint())
                .into(weatherIv)
        } ?: run {
            weatherIv.setImageResource(R.drawable.red_cross)
        }
        tempTv.text = if (item.main?.temp != null) "${item.main?.temp?.kelvToCels()}${DEG_SYMBOL.toChar()}" else NO_VALUE
        timeTv.text = item.dt?.getTime() ?: NO_VALUE
    }
}