package com.orlovdanylo.fromonetoninegame.presentation.menu

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orlovdanylo.fromonetoninegame.base.BaseViewModel
import com.orlovdanylo.fromonetoninegame.data.game.GameRepositoryImpl
import com.orlovdanylo.fromonetoninegame.data.statistics.StatisticRepositoryImpl
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : BaseViewModel(application) {

    private val gameRepository = GameRepositoryImpl(application)
    //private val statisticsRepository = StatisticRepositoryImpl(application)

    val hasStoredGame: MutableLiveData<Boolean> = MutableLiveData(false)
    //val hasStoredStatistics: MutableLiveData<Boolean> = MutableLiveData(false)
    val isGameDeleted: MutableLiveData<Boolean> = MutableLiveData()

    fun checkStoredGame() {
        viewModelScope.launch {
            hasStoredGame.value = gameRepository.isGameSavedInDatabase()
            //hasStoredStatistics.value = statisticsRepository.isStatisticsSavedInDatabase()
        }
    }
}