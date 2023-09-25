package com.orlovdanylo.fromonetoninegame.data.game

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao {

    @Query("SELECT * FROM unfinished_game")
    suspend fun findUnfinishedGame(): GameModelDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gameModel: GameModelDB)

    @Query("DELETE FROM unfinished_game")
    suspend fun deleteUnfinishedGame()
}