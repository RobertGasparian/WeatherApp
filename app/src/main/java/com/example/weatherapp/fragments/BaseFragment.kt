package com.example.weatherapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.activities.Navigator
import com.example.weatherapp.viewmodels.BaseViewModel
import com.example.weatherapp.viewmodels.implementations.HomeViewModelImpl
import com.example.weatherapp.viewmodels.interfaces.HomeViewModel

abstract class BaseFragment : Fragment() {

    @get:LayoutRes
    abstract val resourceId: Int

    protected var navigator: Navigator? = null

    protected open val baseViewModel: BaseViewModel?
    get() = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(resourceId, container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        setupLayout()
        setupClicks()
    }

    protected fun <T>getViewModel(modelClass: Class<T>): T {
        return when(modelClass) {
            is HomeViewModel -> ViewModelProviders.of(this).get(HomeViewModelImpl::class.java) as T
            else -> throw RuntimeException("Wrong class type")
        }
    }

    abstract fun setupLayout()

    abstract fun setupClicks()

    abstract fun bindViewModel()


}