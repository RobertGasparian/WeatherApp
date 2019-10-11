package com.example.weatherapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModelImpl(application: Application) : AndroidViewModel(application), BaseViewModel {

}