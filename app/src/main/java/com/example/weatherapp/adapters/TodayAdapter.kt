package com.example.weatherapp.adapters

import android.view.ViewGroup
import com.example.weatherapp.adapters.viewholders.TodayViewHolder

class TodayAdapter(items: List<ListItem>) : BaseAdapter<TodayViewHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

}