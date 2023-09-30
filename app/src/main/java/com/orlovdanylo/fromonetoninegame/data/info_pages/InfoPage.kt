package com.orlovdanylo.fromonetoninegame.data.info_pages

import com.orlovdanylo.fromonetoninegame.presentation.game.models.GameModel

data class InfoPage(
    val descriptionResId: Int,
    val listOfModels: List<GameModel>
)