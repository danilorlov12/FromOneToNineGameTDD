package com.orlovdanylo.fromonetoninegame.presentation.game.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.presentation.game.GameModel

class GameAdapter(
    private val clickListener: ClickListener,
) : ListAdapter<GameModel, GameAdapter.GameModelViewHolder>(GameDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameModelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game_model, parent, false)
        return GameModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameModelViewHolder, position: Int) {
        val model = getItem(position)
        with(holder) {
            tvNumber.apply {
                text = if (!model.isCrossed) model.num.toString() else ""
                setTextColor(
                    ContextCompat.getColor(
                        itemView.context, if (model.isSelected) {
                            R.color.red
                        } else R.color.gold
                    )
                )
            }
            itemView.setOnClickListener {
                clickListener.click(model)
            }
        }
    }

    inner class GameModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
    }
}

interface ClickListener {
    fun click(model: GameModel)
}