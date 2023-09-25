package com.orlovdanylo.fromonetoninegame.domain.model

data class StatisticsModel(
    val gamesPlayed: Int,
    val gamesFinished: Int,
    val bestTime: String,
    val minPairs: Int,
    val maxPairs: Int
)