package com.example.weatherapp.fragments.dialogs

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.weatherapp.R
import kotlinx.android.synthetic.main.dialog_fragment_error.*

class ErrorDialogFragment : DialogFragment() {

    private var dismissAction:(() -> Unit)? = null
    private var message: String? = null

    companion object {
        private const val MESSAGE_KEY = "message_key"
        fun newInstance(message: String? = null, dismissAction:() -> Unit): ErrorDialogFragment {
            return ErrorDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(MESSAGE_KEY, message)
                }
                this.dismissAction = dismissAction
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_error, container, false)
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        errorTitleTv.text = getString(R.string.error_title)
        arguments?.getString(MESSAGE_KEY)?.let { msg ->
            message = msg
        } ?: run {
            message = getString(R.string.unknown_error)
        }
        messageTv.text = message
        okBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        dismissAction?.invoke()
        super.onDismiss(dialog)
    }

}