package com.orlovdanylo.fromonetoninegame.presentation.game

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orlovdanylo.fromonetoninegame.base.BaseViewModel
import com.orlovdanylo.fromonetoninegame.data.GameModelDB
import com.orlovdanylo.fromonetoninegame.data.repository.GameRepositoryImpl
import com.orlovdanylo.fromonetoninegame.presentation.game.models.GameModel
import com.orlovdanylo.fromonetoninegame.presentation.game.models.NumberRemoval
import com.orlovdanylo.fromonetoninegame.presentation.game.undo_redo_operations.IUndoRedoOperation
import com.orlovdanylo.fromonetoninegame.presentation.game.undo_redo_operations.UndoRedoOperation
import com.orlovdanylo.fromonetoninegame.utils.GameUtils
import kotlinx.coroutines.launch

class GameViewModel(
    application: Application
) : BaseViewModel(application), IUndoRedoOperation by UndoRedoOperation() {

    private val repository = GameRepositoryImpl(application)

    val gameModels: MutableLiveData<MutableList<GameModel>> = MutableLiveData()
    val gameModelsCount: MutableLiveData<Int> = MutableLiveData()
    val selectedModel: MutableLiveData<GameModel?> = MutableLiveData()
    val pairNumbers: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    val isGameFinished: MutableLiveData<Boolean> = MutableLiveData()
    val startTime: MutableLiveData<Long> = MutableLiveData()
    val gameTime: MutableLiveData<Long> = MutableLiveData()

    fun initGame(isNewGame: Boolean) {
        viewModelScope.launch {
            gameModels.value = if (isNewGame) {
                GameUtils.game.mapIndexed { index, s ->
                    GameModel(index, s.toInt(), false)
                }
            } else {
                val storedGame = repository.getLastGameFromDatabase()
                convertToDisplayableGame(storedGame!!)
            }.toMutableList()
            gameModelsCount.value = gameModels.value!!.count { !it.isCrossed }
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

        gameModels.value = (gameModels.value!! + restValues.mapIndexed { index, model ->
            GameModel(index + lastModelId, model.num, false)
        }).toMutableList()
        gameModelsCount.value = gameModels.value!!.count { !it.isCrossed }

        updateStacks(arrayListOf(), arrayListOf())
    }

    private fun checkNumbers(gameModel: GameModel) {
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
        val startModel = gameModels.value!![start]
        val endModel = gameModels.value!![end]

        updateStacks(ArrayList(undoStack.value!! + NumberRemoval(startModel, endModel)), arrayListOf())

        gameModels.value!![start] = startModel.copy(isCrossed = true)
        gameModels.value!![end] = endModel.copy(isCrossed = true)

        pairNumbers.value = start to end

        gameModelsCount.value = gameModels.value!!.count { !it.isCrossed }

        if (gameModelsCount.value == 0) {
            isGameFinished.value = true
        }
    }

    private fun convertToDisplayableGame(gameDbModel: GameModelDB): List<GameModel> {
        return gameDbModel.gameDigits.mapIndexed { index, c ->
            GameModel.fromMapIndexed(index, c.toString())
        }
    }

    fun prepareGameModelToSave() {
        viewModelScope.launch {
            val gameDbModel = GameModelDB(
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