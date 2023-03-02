package com.example.fromonetoninegame.utils

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock

abstract class CountUpTimer(
    private var base: Long = 0,
    private val interval: Long
) {

    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            synchronized(this@CountUpTimer) {
                val elapsedTime = SystemClock.elapsedRealtime() - base
                onTick(elapsedTime)
                sendMessageDelayed(obtainMessage(MSG), interval)
            }
        }
    }

    fun start() {
        base = SystemClock.elapsedRealtime() - base
        handler.sendMessage(handler.obtainMessage(MSG))
    }

    fun stop() {
        handler.removeMessages(MSG)
    }

    fun reset() {
        synchronized(this) { handler.sendMessage(handler.obtainMessage(MSG)) }
    }

    abstract fun onTick(elapsedTime: Long)

    companion object {
        private const val MSG = 1
    }
}