package com.orlovdanylo.fromonetoninegame.data.statistics

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statistics")
data class StatisticsModelEntity (
    @PrimaryKey
    val id: Int,
    val gamesPlayed: Int?,
    val gamesFinished: Int?,
    val bestTime: String?,
    val minPairs: Int?,
    val maxPairs: Int?
)