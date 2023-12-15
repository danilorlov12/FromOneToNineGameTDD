package com.orlovdanylo.fromonetoninegame.utils

enum class GameMode(val numbers: List<String>) {
    NORMAL(
        listOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "1", "1", "1", "2", "1", "3", "1", "4", "1",
            "5", "1", "6", "1", "7", "1", "8", "1", "9"
        )
    ),
    TEST(
        listOf(
            "1", "1", "1", "1", "1", "1", "1", "1", "9",
            "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "1", "1", "1", "1", "1", "1", "1", "1", "9"
        )
    )
}