package com.example.weatherapp.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder(protected val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    abstract fun <T>bind(item: T)
}