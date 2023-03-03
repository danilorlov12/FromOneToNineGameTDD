package com.orlovdanylo.fromonetoninegame.presentation.alert_dialog

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.orlovdanylo.fromonetoninegame.R

class CustomAlertDialog(
    context: Context,
    private val title: String?,
    private val message: String?,
    private val positiveButtonText: String,
    private val negativeButtonText: String?,
    private val onPositiveButtonClick: () -> Unit,
    private val onNegativeButtonClick: () -> Unit
) : AlertDialog(context, R.style.AlertDialogStyle) {

    constructor(
        context: Context,
        title: String?,
        positiveButtonText: String,
        onPositiveButtonClick: () -> Unit
    ) : this(context, title, null, positiveButtonText, null, onPositiveButtonClick, {})

    override fun create() {
        super.create()
        setContentView(R.layout.custom_alert_dialog)
        setCancelable(false)

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        if (title != null) {
            tvTitle.text = title
        } else {
            tvTitle.visibility = View.GONE
        }

//        val tvMessage = findViewById<TextView>(R.id.tvMessage)
//        if (title != null) {
//            tvMessage.text = title
//        } else {
//            tvMessage.visibility = View.GONE
//        }

        findViewById<AppCompatButton>(R.id.btnOk).apply {
            text = positiveButtonText
            setOnClickListener {
                onPositiveButtonClick.invoke()
                dismiss()
            }
        }

//        val negativeButton = findViewById<AppCompatButton>(R.id.btnCancel)
//        if (negativeButtonText != null) {
//            negativeButton.text = negativeButtonText
//        } else {
//            negativeButton.visibility = View.GONE
//        }
//        negativeButton.setOnClickListener {
//            onNegativeButtonClick.invoke()
//            dismiss()
//        }
        show()
    }
}