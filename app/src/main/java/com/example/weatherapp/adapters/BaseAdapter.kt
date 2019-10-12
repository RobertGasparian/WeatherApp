package com.example.weatherapp.adapters

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.viewholders.BaseViewHolder

abstract class BaseAdapter<T: BaseViewHolder<*>, V: ListItem>(private var listItems: List<ListItem?>) : RecyclerView.Adapter<T>() {

    override fun getItemCount(): Int {
        return listItems.size
    }

    @CallSuper
    open fun updateItemList(itemList: List<V?>) {
        listItems = itemList
    }
}