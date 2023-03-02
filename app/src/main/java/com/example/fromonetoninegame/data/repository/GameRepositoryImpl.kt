package com.example.fromonetoninegame.data.repository

import android.app.Application
import com.example.fromonetoninegame.data.AppDatabase
import com.example.fromonetoninegame.data.GameDbModel
import com.example.fromonetoninegame.domain.GameRepository

class GameRepositoryImpl(
    application: Application
) : GameRepository {

    private val gameDao = AppDatabase.getInstance(application).gameDao()

    override suspend fun saveGameToDatabase(gameDbModel: GameDbModel) {
        gameDao.insert(gameDbModel)
    }

    override suspend fun isGameSavedInDatabase(): Boolean {
        return gameDao.findUnfinishedGame() != null
    }

    override suspend fun getLastGameFromDatabase(): GameDbModel? {
        return gameDao.findUnfinishedGame()
    }

    override suspend fun deleteLastGameFromDatabase() {
        gameDao.deleteUnfinishedGame()
    }
}
