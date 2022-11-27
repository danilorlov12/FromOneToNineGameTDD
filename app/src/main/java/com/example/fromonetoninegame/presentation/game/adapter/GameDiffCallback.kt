package com.example.fromonetoninegame.presentation.game.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fromonetoninegame.models.Model

object GameDiffCallback : DiffUtil.ItemCallback<Model>() {

    override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
        return oldItem == newItem
    }
}