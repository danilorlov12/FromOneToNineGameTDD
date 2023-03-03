package com.example.fromonetoninegame.domain

import com.example.fromonetoninegame.data.GameModelDB

interface GameRepository {

    suspend fun saveGameToDatabase(gameDbModel: GameModelDB)

    suspend fun isGameSavedInDatabase(): Boolean

    suspend fun getLastGameFromDatabase(): GameModelDB?

    suspend fun deleteLastGameFromDatabase()
}