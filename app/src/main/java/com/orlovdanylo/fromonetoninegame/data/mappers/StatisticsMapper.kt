package com.orlovdanylo.fromonetoninegame.data.mappers

import com.orlovdanylo.fromonetoninegame.data.statistics.StatisticsModelEntity
import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel
import com.orlovdanylo.fromonetoninegame.domain.model.TimeModel

class StatisticsMapper {

    fun toStatisticsModel(statisticsModelEntity: StatisticsModelEntity?) : StatisticsModel {
        val timeModel = TimeModel(statisticsModelEntity?.bestTime?.toLongOrNull() ?: 0L)
        return StatisticsModel(
            gamesPlayed = statisticsModelEntity?.gamesPlayed ?: 0,
            gamesFinished = statisticsModelEntity?.gamesFinished ?: 0,
            bestTime = timeModel.displayableTime(),
            minPairs = statisticsModelEntity?.minPairs ?: 0,
            maxPairs = statisticsModelEntity?.maxPairs ?: 0
        )
    }
}