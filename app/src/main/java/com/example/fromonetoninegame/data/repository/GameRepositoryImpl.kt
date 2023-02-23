package com.example.fromonetoninegame.data.repository

import android.app.Application
import android.util.Log
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
        val game = gameDao.findUnfinishedGame()
        Log.e("game = %s", game.toString())
        return gameDao.findUnfinishedGame()
    }
}
