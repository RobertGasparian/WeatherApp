package com.example.weatherapp.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.viewholders.BaseViewHolder

abstract class BaseAdapter<T: BaseViewHolder>(protected var itemList: List<ListItem>) : RecyclerView.Adapter<T>() {

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateItemList(itemList: List<ListItem>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
}