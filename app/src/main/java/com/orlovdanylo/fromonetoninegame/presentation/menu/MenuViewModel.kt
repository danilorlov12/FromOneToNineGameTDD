package com.orlovdanylo.fromonetoninegame.presentation.menu

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orlovdanylo.fromonetoninegame.Repositories
import com.orlovdanylo.fromonetoninegame.base.BaseViewModel
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : BaseViewModel(application) {

    private val gameRepository = Repositories.gameRepository
    val hasStoredGame: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkStoredGame() {
        viewModelScope.launch {
            hasStoredGame.value = gameRepository.isGameSavedInDatabase()
        }
    }
}