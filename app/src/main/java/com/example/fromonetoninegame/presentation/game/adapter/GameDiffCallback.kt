package com.example.fromonetoninegame.presentation.game.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fromonetoninegame.presentation.game.GameModel

object GameDiffCallback : DiffUtil.ItemCallback<GameModel>() {

    override fun areItemsTheSame(oldItem: GameModel, newItem: GameModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameModel, newItem: GameModel): Boolean {
        return oldItem == newItem
    }
}