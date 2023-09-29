package com.orlovdanylo.fromonetoninegame.data.game

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unfinished_game")
data class GameModelDB(
    @PrimaryKey
    val id: Int,
    val gameDigits: String,
    val time: Long,
    val pairCrossed: Int,
)
