package com.orlovdanylo.fromonetoninegame.domain

import com.orlovdanylo.fromonetoninegame.data.game.GameModelDB

interface GameRepository {
    suspend fun saveGameToDatabase(gameDbModel: GameModelDB)
    suspend fun isGameSavedInDatabase(): Boolean
    suspend fun getLastGameFromDatabase(): GameModelDB?
    suspend fun deleteLastGameFromDatabase()
}