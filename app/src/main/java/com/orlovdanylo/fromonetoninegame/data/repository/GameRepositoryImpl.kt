package com.orlovdanylo.fromonetoninegame.data.repository

import android.app.Application
import com.orlovdanylo.fromonetoninegame.data.AppDatabase
import com.orlovdanylo.fromonetoninegame.data.GameModelDB
import com.orlovdanylo.fromonetoninegame.domain.GameRepository

class GameRepositoryImpl(
    application: Application
) : GameRepository {

    private val gameDao = AppDatabase.getInstance(application).gameDao()

    override suspend fun saveGameToDatabase(gameDbModel: GameModelDB) {
        gameDao.insert(gameDbModel)
    }

    override suspend fun isGameSavedInDatabase(): Boolean {
        return gameDao.findUnfinishedGame() != null
    }

    override suspend fun getLastGameFromDatabase(): GameModelDB? {
        return gameDao.findUnfinishedGame()
    }

    override suspend fun deleteLastGameFromDatabase() {
        gameDao.deleteUnfinishedGame()
    }
}
