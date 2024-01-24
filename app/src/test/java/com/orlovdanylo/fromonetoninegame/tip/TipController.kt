package com.orlovdanylo.fromonetoninegame.tip

import com.orlovdanylo.fromonetoninegame.presentation.game.models.GameModel
import com.orlovdanylo.fromonetoninegame.utils.GameController

class TipController(
    private val models: List<GameModel>
) {

    fun fetchAvailablePair(): TipResult {
        val gameController = GameController(models)
        val visitedIds = mutableSetOf<Int>()
        val pairs: MutableList<Pair<Int, Int>> = mutableListOf()

        models.filter { !it.isCrossed }.forEachIndexed { index, model ->
            visitedIds.add(model.id)
            models.subList(index + 1, models.size).filter { it.id !in visitedIds }.firstOrNull { secondModel ->
                gameController.determineRemovableNumberIds(model, secondModel)?.let {
                    pairs.add(Pair(it.first, it.second))
                }
                false
            }
        }
        return if (pairs.isNotEmpty()) TipResult.AvailablePairs(pairs) else TipResult.NotFound
    }
}