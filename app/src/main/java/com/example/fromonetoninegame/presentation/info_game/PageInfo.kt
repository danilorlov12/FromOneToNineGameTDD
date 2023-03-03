package com.example.fromonetoninegame.presentation.info_game

import com.example.fromonetoninegame.presentation.game.GameModel

data class PageInfo(
    val descriptionResId: Int,
    val listOfModels: List<GameModel>
)