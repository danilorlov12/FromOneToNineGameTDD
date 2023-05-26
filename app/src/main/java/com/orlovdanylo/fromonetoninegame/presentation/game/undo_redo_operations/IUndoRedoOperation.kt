package com.orlovdanylo.fromonetoninegame.presentation.game.undo_redo_operations

import androidx.lifecycle.MutableLiveData
import com.orlovdanylo.fromonetoninegame.presentation.game.GameModel
import com.orlovdanylo.fromonetoninegame.presentation.game.NumberRemoval

interface IUndoRedoOperation {
    val undoStack: MutableLiveData<MutableList<NumberRemoval>>
    val redoStack: MutableLiveData<MutableList<NumberRemoval>>

    fun undo(gameModels: MutableList<GameModel>)
    fun redo(gameModels: MutableList<GameModel>)
}