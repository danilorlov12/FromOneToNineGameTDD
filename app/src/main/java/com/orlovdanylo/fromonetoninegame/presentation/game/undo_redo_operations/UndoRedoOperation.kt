package com.orlovdanylo.fromonetoninegame.presentation.game.undo_redo_operations

import androidx.lifecycle.MutableLiveData
import com.orlovdanylo.fromonetoninegame.presentation.game.GameModel
import com.orlovdanylo.fromonetoninegame.presentation.game.NumberRemoval

class UndoRedoOperation : IUndoRedoOperation {

    override val undoStack: MutableLiveData<MutableList<NumberRemoval>> =
        MutableLiveData(arrayListOf())

    override val redoStack: MutableLiveData<MutableList<NumberRemoval>> =
        MutableLiveData(arrayListOf())

    override fun undo(gameModels: MutableList<GameModel>) {
        val lastNumberRemoval = undoStack.value!!.removeLast()
        val firstModel = lastNumberRemoval.number1
        val secondModel = lastNumberRemoval.number2

        gameModels[firstModel.id] = firstModel
        gameModels[secondModel.id] = secondModel

        redoStack.value!!.add(
            NumberRemoval(
                lastNumberRemoval.number1.copy(isCrossed = true),
                lastNumberRemoval.number2.copy(isCrossed = true)
            )
        )
    }

    override fun redo(gameModels: MutableList<GameModel>) {
        val lastNumberCanceled = redoStack.value!!.removeLast()
        val firstModel = lastNumberCanceled.number1
        val secondModel = lastNumberCanceled.number2

        gameModels[firstModel.id] = firstModel
        gameModels[secondModel.id] = secondModel

        undoStack.value!!.add(lastNumberCanceled)
    }
}