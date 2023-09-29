package com.orlovdanylo.fromonetoninegame.data.statistics

import com.orlovdanylo.fromonetoninegame.data.mappers.StatisticsMapper
import com.orlovdanylo.fromonetoninegame.domain.StatisticsRepository
import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel
import com.orlovdanylo.fromonetoninegame.utils.smallestNonzero

class StatisticRepositoryImpl(
    private val statisticsDao: StatisticsDao
) : StatisticsRepository {

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
            bestTime = (time smallestNonzero (model.bestTime?.toLongOrNull() ?: 0L)).toString(),
            minPairs = if (model.minPairs == 0 || model.minPairs!! > pairs) pairs else model.minPairs,
            maxPairs = if (model.maxPairs!! < pairs) pairs else model.maxPairs
        ))
    }

    private suspend fun statisticsModel(): StatisticsModelEntity {
        val statistics = statisticsDao.getStatistics()
        return StatisticsModelEntity(
            id = statistics?.id ?: 0,
            gamesPlayed = statistics?.gamesPlayed ?: 0,
            gamesFinished = statistics?.gamesFinished ?: 0,
            bestTime = statistics?.bestTime ?: "0",
            minPairs = statistics?.minPairs ?: 0,
            maxPairs = statistics?.maxPairs ?: 0
        )
    }
}