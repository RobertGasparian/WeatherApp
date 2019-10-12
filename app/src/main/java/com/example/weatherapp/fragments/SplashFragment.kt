package com.example.weatherapp.fragments

import com.example.weatherapp.R

class SplashFragment : BaseFragment() {

    companion object {
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }

    override val resourceId: Int
        get() = R.layout.fragment_splash

    override fun setupLayout() {
        //do nothing
    }

    override fun setupListeners() {
        //do nothing
    }

    override fun bindViewModel() {
        //do nothing
    }
}