package com.example.fromonetoninegame.presentation.game

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fromonetoninegame.base.BaseViewModel
import com.example.fromonetoninegame.data.GameDbModel
import com.example.fromonetoninegame.data.repository.GameRepositoryImpl
import com.example.fromonetoninegame.models.Model
import com.example.fromonetoninegame.utils.GameUtils
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : BaseViewModel(application) {

    private val repository = GameRepositoryImpl(application)

    val gameModels: MutableLiveData<List<Model>> = MutableLiveData()
    val selectedModel: MutableLiveData<Model?> = MutableLiveData()
    val pairNumbers: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    val startTime: MutableLiveData<Long> = MutableLiveData()
    val gameTime: MutableLiveData<Long> = MutableLiveData()

    fun initGame(isNewGame: Boolean) {
        viewModelScope.launch {
            gameModels.value = if (isNewGame) {
                GameUtils.game.mapIndexed { index, s ->
                    Model(index, s.toInt(), false)
                }
            } else {
                val storedGame = repository.getLastGameFromDatabase()
                convertToDisplayableGame(storedGame!!)
            }
        }
    }

    fun initGameTime() {
        viewModelScope.launch {
            val storedGame = repository.getLastGameFromDatabase()
            startTime.value = storedGame?.time ?: 0L
        }
    }

    fun tap(id: Int) {
        val gameModel = gameModels.value!!.find { it.id == id } ?: return

        if (gameModel.isCrossed) return

        if (gameModel.id == (selectedModel.value?.id ?: false)) {
            selectedModel.value = null
            return
        }

        selectedModel.value = if (selectedModel.value == null) {
            gameModel
        } else {
            checkNumbers(gameModel)
            null
        }
    }

    fun updateNumbers() {
        val lastModelId = gameModels.value!!.last().id + 1
        val restValues = gameModels.value!!.filter { !it.isCrossed }

        gameModels.value = gameModels.value!! + restValues.mapIndexed { index, model ->
            Model(index + lastModelId, model.num, false)
        }
    }

    private fun checkNumbers(gameModel: Model) {
        if (GameUtils.checkNumbers(selectedModel.value!!.num, gameModel.num)) {
            val (start, end) = if (selectedModel.value!!.id < gameModel.id) {
                selectedModel.value!!.id to gameModel.id
            } else {
                gameModel.id to selectedModel.value!!.id
            }

            if (start == end - 1 || start == end - 9) {
                setValuesCrossed(start, end)
                return
            }
            if (GameUtils.canItBeCovered(gameModels.value!!, start, end)) {
                setValuesCrossed(start, end)
            }
        }
    }

    private fun setValuesCrossed(start: Int, end: Int) {
        gameModels.value!![start].isCrossed = true
        gameModels.value!![end].isCrossed = true

        pairNumbers.value = start to end
    }

    private fun convertToDisplayableGame(gameDbModel: GameDbModel): List<Model> {
        return gameDbModel.gameDigits.mapIndexed { index, c ->
            Model(
                id = index,
                num = c.toString().toInt(),
                isCrossed = c.toString().toInt() == 0,
                isSelected = false
            )
        }
    }

    fun prepareGameModelToSave() {
        viewModelScope.launch {
            val gameDbModel = GameDbModel(
                id = 0,
                gameDigits = gameModels.value?.joinToString("") {
                    if (it.isCrossed) "0" else it.num.toString()
                } ?: "",
                time = gameTime.value ?: 0L,
                pairCrossed = "pairCrossed"
            )
            repository.saveGameToDatabase(gameDbModel)
        }
    }
}