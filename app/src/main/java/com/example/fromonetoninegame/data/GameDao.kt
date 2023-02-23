package com.example.fromonetoninegame.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao {

    @Query("SELECT * FROM unfinished_game")
    suspend fun findUnfinishedGame(): GameDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gameModel: GameDbModel)
}