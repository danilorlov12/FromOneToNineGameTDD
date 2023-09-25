package com.orlovdanylo.fromonetoninegame.domain

import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel

interface StatisticsRepository {

    suspend fun isStatisticsSavedInDatabase(): Boolean

    suspend fun getStatistics(): StatisticsModel?

    suspend fun updateStatistics(statistics: StatisticsModel)
}