package com.example.fromonetoninegame.models

data class Model(
    val id: Int,
    var num: Int,
    var isCrossed: Boolean,
    var isSelected: Boolean = false
)
