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
    private val mapper = StatisticsMapper()

    override suspend fun getStatistics(): StatisticsModel {
        return mapper.toStatisticsModel(statisticsDao.getStatistics())
    }

    override suspend fun increasePlayedGame() {
        val model = statisticsModel()
        statisticsDao.updateStatistics(model.copy(gamesPlayed = model.gamesPlayed!! + 1))
    }

    override suspend fun updateFinishedGameStatistics(time: Long, pairs: Int) {
        val model = statisticsModel()
        statisticsDao.updateStatistics(model.copy(
            gamesFinished = model.gamesFinished!! + 1,
            bestTime = model.bestTime,
            minPairs = if (model.minPairs != 0 && model.minPairs!! > pairs) pairs else model.minPairs,
            maxPairs = if (model.maxPairs!! < pairs) pairs else model.maxPairs
        ))
    }

    private suspend fun statisticsModel(): StatisticsModelEntity {
        val statistics = statisticsDao.getStatistics()
        return StatisticsModelEntity(
            id = statistics?.id ?: 0,
            gamesPlayed = statistics?.gamesPlayed ?: 0,
            gamesFinished = statistics?.gamesFinished ?: 0,
            bestTime = statistics?.bestTime ?: "00:00:00",
            minPairs = statistics?.minPairs ?: 0,
            maxPairs = statistics?.maxPairs ?: 0
        )
    }
}