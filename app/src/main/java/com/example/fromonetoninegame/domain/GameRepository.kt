package com.example.fromonetoninegame.domain

import com.example.fromonetoninegame.data.GameDbModel

interface GameRepository {

    suspend fun saveGameToDatabase(gameDbModel: GameDbModel)

    suspend fun isGameSavedInDatabase(): Boolean

    suspend fun getLastGameFromDatabase(): GameDbModel?
}