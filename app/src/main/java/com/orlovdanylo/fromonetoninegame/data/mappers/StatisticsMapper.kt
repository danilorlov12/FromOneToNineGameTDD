package com.orlovdanylo.fromonetoninegame.data.mappers

import com.orlovdanylo.fromonetoninegame.data.statistics.StatisticsModelEntity
import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel

class StatisticsMapper {

    fun toStatisticsModel(statisticsModelEntity: StatisticsModelEntity?) : StatisticsModel {
        return StatisticsModel(
            gamesPlayed = statisticsModelEntity?.gamesPlayed ?: 0,
            gamesFinished = statisticsModelEntity?.gamesFinished ?: 0,
            bestTime = statisticsModelEntity?.bestTime ?: "00:00:00",
            minPairs = statisticsModelEntity?.minPairs ?: 0,
            maxPairs = statisticsModelEntity?.maxPairs ?: 0
        )
    }
}