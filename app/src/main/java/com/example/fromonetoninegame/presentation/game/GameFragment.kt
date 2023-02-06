package com.example.fromonetoninegame.presentation.game

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
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

        val gameAdapter = GameAdapter(object : ClickListener {
            override fun click(model: Model) {
                viewModel.tap(model.id)
            }
        })
        view.findViewById<RecyclerView>(R.id.rvDigits).apply {
            layoutManager = GridLayoutManager(context, 9)
            adapter = gameAdapter
            itemAnimator = null
        }
        view.findViewById<AppCompatButton>(R.id.btnUpdateModels).setOnClickListener {
            viewModel.updateNumbers()
        }

        viewModel.init()

        viewModel.gameModels.observe(viewLifecycleOwner) { models ->
            if (models.isNullOrEmpty()) return@observe

            gameAdapter.submitList(models)
        }
        viewModel.selectedModel.observe(viewLifecycleOwner) { model ->
            val item = if (model != null) {
                val item = viewModel.gameModels.value!!.first { it.id == model.id }
                item.isSelected = true
                item
            } else {
                val item = viewModel.gameModels.value!!.first { it.isSelected }
                item.isSelected = false
                item
            }
            gameAdapter.notifyItemChanged(item.id)
        }
        viewModel.pairNumbers.observe(viewLifecycleOwner) { pair ->
            pair.toList().forEach {
                gameAdapter.notifyItemChanged(it)
            }
        }
    }
}