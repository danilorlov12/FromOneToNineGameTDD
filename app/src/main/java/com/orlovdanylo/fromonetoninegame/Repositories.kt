package com.orlovdanylo.fromonetoninegame

import android.content.Context
import androidx.room.Room
import com.orlovdanylo.fromonetoninegame.data.core.AppDatabase
import com.orlovdanylo.fromonetoninegame.data.core.StatisticsMigration
import com.orlovdanylo.fromonetoninegame.data.game.GameRepositoryImpl
import com.orlovdanylo.fromonetoninegame.data.statistics.StatisticRepositoryImpl
import com.orlovdanylo.fromonetoninegame.domain.GameRepository
import com.orlovdanylo.fromonetoninegame.domain.StatisticsRepository

object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "main.db")
            .addMigrations(StatisticsMigration())
            .build()
    }

    val gameRepository: GameRepository by lazy {
        GameRepositoryImpl(database.gameDao())
    }

    val statisticsRepository: StatisticsRepository by lazy {
        StatisticRepositoryImpl(database.statisticsDao())
    }

    fun init(context: Context) {
        applicationContext = context
    }
}