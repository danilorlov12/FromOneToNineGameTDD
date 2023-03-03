package com.example.fromonetoninegame.utils

import com.example.fromonetoninegame.presentation.game.GameModel

object GameUtils {

    val _game = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "1", "1", "1", "2", "1", "3", "1", "4", "1",
        "5", "1", "6", "1", "7", "1", "8", "1", "9"
    )

    val game = listOf(
        "1", "1", "1", "1", "1", "1"
    )

    fun checkNumbers(firstValue: Int, secondValue: Int): Boolean {
        return firstValue + secondValue == 10 || firstValue == secondValue
    }

    fun canItBeCovered(models: List<GameModel>, start: Int, end: Int): Boolean {
        return canItBeCoveredByRow(models, start, end) || canItBeCoveredByColumn(models, start, end)
    }

    private fun canItBeCoveredByRow(models: List<GameModel>, start: Int, end: Int): Boolean {
        for (i in start + 1 until end) {
            if (!models[i].isCrossed) return false
        }
        return true
    }

    private fun canItBeCoveredByColumn(models: List<GameModel>, start: Int, end: Int): Boolean {
        if ((end - start) % 9 != 0) return false

        for (i in start + 9 until end step 9) {
            if (!models[i].isCrossed && i != end) return false
        }
        return true
    }
}