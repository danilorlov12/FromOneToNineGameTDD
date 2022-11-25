package com.example.onetoninegame.utils

import com.example.onetoninegame.models.Model

object GameUtils {

    val game = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "1", "1", "1", "2", "1", "3", "1", "4", "1",
        "5", "1", "6", "1", "7", "1", "8", "1", "9"
    )

    fun checkNumbers(firstValue: Int, secondValue: Int): Boolean {
        return firstValue + secondValue == 10 || firstValue == secondValue
    }

    fun canItBeCovered(models: List<Model>, start: Int, end: Int): Boolean {
        return canItBeCoveredByRow(models, start, end) || canItBeCoveredByColumn(models, start, end)
    }

    private fun canItBeCoveredByRow(models: List<Model>, start: Int, end: Int): Boolean {
        for (i in start + 1 until end - 1) {
            if (!models[i].isCrossed) return false
        }
        return true
    }

    private fun canItBeCoveredByColumn(models: List<Model>, start: Int, end: Int): Boolean {
        for (i in start + 9 until end - 9 step 9) {
            if (!models[i].isCrossed) return false
        }
        return true
    }
}