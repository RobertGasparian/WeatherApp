package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.adapters.viewholders.TodayViewHolder
import com.example.weatherapp.data.models.WeatherData

class TodayAdapter(private var items: List<WeatherData?>) : BaseAdapter<TodayViewHolder, WeatherData>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_today, parent, false)
        return TodayViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun updateItemList(itemList: List<WeatherData?>) {
        super.updateItemList(itemList)
        items = itemList
        notifyDataSetChanged()
    }

}