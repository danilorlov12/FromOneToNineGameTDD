package com.orlovdanylo.fromonetoninegame.domain

import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel

interface StatisticsRepository {
    suspend fun getStatistics(): StatisticsModel?
    suspend fun increasePlayedGame()
    suspend fun updateFinishedGameStatistics(time: Long, pairs: Int)
}