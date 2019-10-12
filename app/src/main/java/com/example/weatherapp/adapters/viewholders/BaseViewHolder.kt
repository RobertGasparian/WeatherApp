package com.example.weatherapp.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.ListItem
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<T: ListItem>(protected val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    abstract fun bind(item: T?)
}