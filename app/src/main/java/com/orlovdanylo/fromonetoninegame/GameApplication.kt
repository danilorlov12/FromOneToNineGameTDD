package com.orlovdanylo.fromonetoninegame

import android.app.Application
import com.orlovdanylo.fromonetoninegame.data.core.AppDatabase

class GameApplication : Application() {

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(applicationContext)
    }
}