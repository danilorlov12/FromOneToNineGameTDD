package com.orlovdanylo.fromonetoninegame.presentation.sms_code

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.base.BaseFragment

class SMSCodeFragment : BaseFragment<SMSCodeViewModel>() {
    override val viewModel: SMSCodeViewModel by activityViewModels()
    override val layoutId: Int = R.layout.fragment_sms_code

    private var intentFilter: IntentFilter? = null
    private var smsCodeReceiver: SMSCodeReceiver? = null

    private val tvCode: TextView by lazy {
        requireView().findViewById(R.id.tvCode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireContext(), AppSignatureProvider(requireContext()).getAppHashCode(), Toast.LENGTH_LONG).show()

        initSMSCodeReceiver()
        smsListener()

        tvCode.text = "####"

        //        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},1);
        //        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.RECEIVE_SMS), 1)
    }

    private fun smsListener() {
        val client = SmsRetriever.getClient(requireActivity())
        client.startSmsRetriever()
    }

    private fun initSMSCodeReceiver() {
        intentFilter = IntentFilter("com.google.android.gms.auth.api.phone.SMS_RETRIEVED")
        intentFilter?.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        intentFilter?.addAction("android.provider.Telephony.SMS_RECEIVED")

        smsCodeReceiver = SMSCodeReceiver().apply {
            setOnSMSReceiveListener(object : SMSCodeReceiver.OnSMSReceiveListener {
                override fun onReceive(code: String?) {
                    tvCode.text = ""
                    tvCode.text = code
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("tag", "onResume")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().registerReceiver(smsCodeReceiver, intentFilter, AppCompatActivity.RECEIVER_NOT_EXPORTED)
        } else {
            requireActivity().registerReceiver(smsCodeReceiver, intentFilter)
            //requireActivity().registerReceiver(smsCodeReceiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION), Context.RECEIVER_NOT_EXPORTED);
        }
    }

    override fun onPause() {
        super.onPause()
        Log.e("tag", "onPause")
        requireActivity().unregisterReceiver(smsCodeReceiver)
    }

    override fun clear() = Unit
}

class SMSCodeReceiver : BroadcastReceiver() {

    private var onSMSReceiveListener: OnSMSReceiveListener? = null

    fun setOnSMSReceiveListener(onSMSReceiveListener: OnSMSReceiveListener) {
        this.onSMSReceiveListener = onSMSReceiveListener
    }

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "${intent.action}", Toast.LENGTH_LONG).show()

        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val status = extras?.get(SmsRetriever.EXTRA_STATUS) as? Status
            when (status?.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as? String
                    message?.extractCodeFromMessage()?.let { code ->
                        onSMSReceiveListener?.onReceive(code)
                    }
                }

                CommonStatusCodes.TIMEOUT -> {

                }
            }
        }
    }

    interface OnSMSReceiveListener {
        fun onReceive(code: String?)
    }
}

fun String.extractCodeFromMessage(): String {
    return this.filter { it.isDigit() }.take(4)
}