package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.adapters.viewholders.DailyViewHolder
import com.example.weatherapp.adapters.viewholders.TodayViewHolder
import com.example.weatherapp.data.models.AverageDayWeatherDTO

class DailyAdapter(private var items: List<AverageDayWeatherDTO?>) : BaseAdapter<DailyViewHolder, AverageDayWeatherDTO>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily, parent, false)
        return DailyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun updateItemList(itemList: List<AverageDayWeatherDTO?>) {
        super.updateItemList(itemList)
        items = itemList
        notifyDataSetChanged()
    }

}