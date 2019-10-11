package com.example.weatherapp.activities

import com.example.weatherapp.fragments.BaseFragment

interface Navigator {

    fun navigateWithBackStack(fragment: BaseFragment, tag: String? = null)

    fun navigateWithoutBackStack(fragment: BaseFragment)
}