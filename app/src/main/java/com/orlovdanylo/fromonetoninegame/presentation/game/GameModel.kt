package com.orlovdanylo.fromonetoninegame.presentation.game

data class GameModel(
    val id: Int,
    var num: Int,
    var isCrossed: Boolean,
    var isSelected: Boolean = false
)
