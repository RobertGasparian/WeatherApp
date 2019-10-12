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
import com.example.weatherapp.fragments.dialogs.ErrorDialogFragment
import com.example.weatherapp.viewmodels.BaseViewModel
import com.example.weatherapp.viewmodels.implementations.HomeViewModelImpl
import com.example.weatherapp.viewmodels.interfaces.HomeViewModel

abstract class BaseFragment : Fragment() {

    @get:LayoutRes
    abstract val resourceId: Int

    companion object {
        var errorDialogIsShowing = false
    }

    protected var navigator: Navigator? = null

    protected open val baseViewModel: BaseViewModel?
        get() = null

    private var errorDialog: ErrorDialogFragment? = null

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
        setupListeners()
    }

    protected inline fun <reified T : BaseViewModel> getViewModel(): T {
        return when (T::class.java.name) {
            HomeViewModel::class.java.name -> ViewModelProviders.of(this).get(HomeViewModelImpl::class.java) as T
            else -> throw RuntimeException("Wrong class type")
        }
    }

    abstract fun setupLayout()

    abstract fun setupListeners()

    abstract fun bindViewModel()

    protected open fun showProgress(progressView: View, containerView: View? = null) {
        progressView.visibility = View.VISIBLE
        containerView?.visibility = View.INVISIBLE
    }

    protected open fun hideProgress(progressView: View, containerView: View? = null) {
        progressView.visibility = View.INVISIBLE
        containerView?.visibility = View.VISIBLE
    }

    protected fun showErrorDialog(message: String? = "Unknown Error") {
        if(!errorDialogIsShowing) {
            if (errorDialog == null) {
                errorDialog = ErrorDialogFragment.newInstance(message) {
                    errorDialogIsShowing = false
                }
            } else {
                errorDialog?.setMessage(message)
            }
            fragmentManager?.let { fm ->
                errorDialog?.show(fm, null)
                errorDialogIsShowing = true
            }
        }

    }
}