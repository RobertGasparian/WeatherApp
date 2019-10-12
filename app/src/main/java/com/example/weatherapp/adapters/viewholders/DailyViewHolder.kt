package com.example.weatherapp.adapters.viewholders

import android.annotation.SuppressLint
import android.view.View
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.app.Constants.DEG_SYMBOL
import com.example.weatherapp.app.Constants.ICON_BASE_URL
import com.example.weatherapp.app.Constants.NO_VALUE
import com.example.weatherapp.app.iconEndpoint
import com.example.weatherapp.app.kelvToCels
import com.example.weatherapp.data.models.AverageDayWeatherDTO
import kotlinx.android.synthetic.main.item_daily.*

class DailyViewHolder(view: View) : BaseViewHolder<AverageDayWeatherDTO>(view) {

    override val containerView: View
        get() = view

    @SuppressLint("SetTextI18n")
    override fun bind(item: AverageDayWeatherDTO?) {
        if (item == null) {
            weatherIv.setImageResource(R.drawable.red_cross)
            dateTv.text = NO_VALUE
            tempTv.text = NO_VALUE
            return
        }
        item.icon?.let { icon ->
            Glide.with(itemView.context)
                .load(ICON_BASE_URL + icon.iconEndpoint())
                .into(weatherIv)
        } ?: run {
            weatherIv.setImageResource(R.drawable.red_cross)
        }
        tempTv.text = "${item.temp.kelvToCels()}${DEG_SYMBOL.toChar()}"
        dateTv.text = item.date ?: NO_VALUE
    }
}