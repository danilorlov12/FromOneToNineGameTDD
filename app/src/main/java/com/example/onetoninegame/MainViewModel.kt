package com.example.onetoninegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onetoninegame.utils.GameUtils

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
            val start = if (selectedModel.value!!.id < gameModel.id) selectedModel.value!!.id else gameModel.id
            val end = if (selectedModel.value!!.id > gameModel.id) selectedModel.value!!.id else gameModel.id

            if (start == end - 1 || start == end - 9) {
                setValuesCrossed(start, end)
                return
            }
            checkRowPositions(start, end)
            checkColumnPositions(start, end)
        }
    }

    private fun checkRowPositions(start: Int, end: Int) {
        for (i in start + 1 until end - 1) {
            if (!gameModels.value!![i].isCrossed) return
        }
        setValuesCrossed(start, end)
    }

    private fun checkColumnPositions(start: Int, end: Int) {
        for (i in start + 9 until end - 9 step 9) {
            if (!gameModels.value!![i].isCrossed) return
        }
        setValuesCrossed(start, end)
    }

    private fun setValuesCrossed(start: Int, end: Int) {
        gameModels.value!![start].isCrossed = true
        gameModels.value!![end].isCrossed = true
    }
}