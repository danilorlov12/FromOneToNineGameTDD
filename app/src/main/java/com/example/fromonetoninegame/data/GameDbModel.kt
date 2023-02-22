package com.example.fromonetoninegame.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unfinished_game")
data class GameDbModel(
    @PrimaryKey
    val id: Int,
    val gameDigits: String,
    val time: String,
    val pairCrossed: String,
)
