package com.example.fromonetoninegame.presentation.menu

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fromonetoninegame.base.BaseViewModel
import com.example.fromonetoninegame.data.repository.GameRepositoryImpl
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : BaseViewModel(application) {

    private val repository = GameRepositoryImpl(application)

    val hasStoredGame: MutableLiveData<Boolean> = MutableLiveData()
    val isGameDeleted: MutableLiveData<Boolean> = MutableLiveData()

    fun checkStoredGame() {
        viewModelScope.launch {
            hasStoredGame.value = repository.isGameSavedInDatabase()
        }
    }

    fun deleteStoredGame() {
        viewModelScope.launch {
            repository.deleteLastGameFromDatabase()
            isGameDeleted.value = true
        }
    }
}