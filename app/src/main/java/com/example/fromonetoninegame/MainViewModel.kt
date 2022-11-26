package com.example.fromonetoninegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fromonetoninegame.models.Model
import com.example.fromonetoninegame.utils.GameUtils

class MainViewModel : ViewModel() {

    var gameModels: MutableLiveData<List<Model>> = MutableLiveData()
    var selectedModel: MutableLiveData<Model> = MutableLiveData()

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
    }
}