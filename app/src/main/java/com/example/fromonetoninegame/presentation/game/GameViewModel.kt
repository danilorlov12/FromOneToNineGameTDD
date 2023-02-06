package com.example.fromonetoninegame.presentation.game

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.fromonetoninegame.base.BaseViewModel
import com.example.fromonetoninegame.models.Model
import com.example.fromonetoninegame.utils.GameUtils

class GameViewModel(app: Application) : BaseViewModel(app) {

    val gameModels: MutableLiveData<List<Model>> = MutableLiveData()
    val selectedModel: MutableLiveData<Model?> = MutableLiveData()
    val pairNumbers: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    fun init() {
        gameModels.value = GameUtils.game.mapIndexed { index, s ->
            Model(index, s.toInt(), false)
        }
    }

    fun tap(id: Int) {
        val gameModel = gameModels.value!!.first { it.id == id }

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
                toCrossValues(start, end)
                return
            }
            if (GameUtils.canItBeCovered(gameModels.value!!, start, end)) {
                toCrossValues(start, end)
            }
        }
    }

    private fun toCrossValues(start: Int, end: Int) {
        gameModels.value!![start].isCrossed = true
        gameModels.value!![end].isCrossed = true

        pairNumbers.value = start to end
    }
}