package com.example.fromonetoninegame.presentation.game

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fromonetoninegame.R
import com.example.fromonetoninegame.base.BaseFragment
import com.example.fromonetoninegame.models.Model
import com.example.fromonetoninegame.presentation.game.adapter.ClickListener
import com.example.fromonetoninegame.presentation.game.adapter.GameAdapter

class GameFragment : BaseFragment<GameViewModel>() {

    override val layoutId: Int = R.layout.fragment_game

    override fun viewModelClass() = GameViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gameAdapter = GameAdapter(
            object : ClickListener {
                override fun click(model: Model) {
                    Toast.makeText(context, model.num.toString(), Toast.LENGTH_LONG).show()
                }
            }
        )

        view.findViewById<RecyclerView>(R.id.rvGame).apply {
            layoutManager = GridLayoutManager(context, 9)
            adapter = gameAdapter
        }

        viewModel.init()

        viewModel.gameModels.observe(viewLifecycleOwner) { models ->
            Toast.makeText(context, models.size.toString(), Toast.LENGTH_LONG).show()
            if (models.isNullOrEmpty()) return@observe

            gameAdapter.submitList(models)
        }
    }
}