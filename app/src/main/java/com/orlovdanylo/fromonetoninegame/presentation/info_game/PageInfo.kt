package com.orlovdanylo.fromonetoninegame.presentation.info_game

import com.orlovdanylo.fromonetoninegame.presentation.game.GameModel

data class PageInfo(
    val descriptionResId: Int,
    val listOfModels: List<GameModel>
)