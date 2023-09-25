package com.orlovdanylo.fromonetoninegame.data.statistics

import android.app.Application
import com.orlovdanylo.fromonetoninegame.data.core.AppDatabase
import com.orlovdanylo.fromonetoninegame.data.mappers.StatisticsMapper
import com.orlovdanylo.fromonetoninegame.domain.StatisticsRepository
import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel

class StatisticRepositoryImpl(
    application: Application
) : StatisticsRepository {

    private val statisticsDao = AppDatabase.getInstance(application).statisticsDao()

    override suspend fun isStatisticsSavedInDatabase(): Boolean {
        return statisticsDao.getStatistics() != null
    }

    override suspend fun getStatistics(): StatisticsModel {
        return StatisticsMapper().toStatisticsModel(statisticsDao.getStatistics())
    }

    override suspend fun updateStatistics(statistics: StatisticsModel) {
        //statisticsDao.updateStatistics(statistics)
    }
}