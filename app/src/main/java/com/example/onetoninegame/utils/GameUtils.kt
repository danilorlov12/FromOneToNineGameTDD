package com.example.onetoninegame.utils

object GameUtils {

    val game = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "1", "1", "1", "2", "1", "3", "1", "4", "1",
        "5", "1", "6", "1", "7", "1", "8", "1", "9"
    )

    fun checkNumbers(firstValue: Int, secondValue: Int): Boolean {
        return firstValue + secondValue == 10 || firstValue == secondValue
    }
}