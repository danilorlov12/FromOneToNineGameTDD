package com.example.fromonetoninegame.presentation.game

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fromonetoninegame.R
import com.example.fromonetoninegame.base.BaseFragment
import com.example.fromonetoninegame.models.Model
import com.example.fromonetoninegame.presentation.game.adapter.ClickListener
import com.example.fromonetoninegame.presentation.game.adapter.GameAdapter
import com.example.fromonetoninegame.utils.CountUpTimer
import java.util.concurrent.TimeUnit

class GameFragment : BaseFragment<GameViewModel>() {

    private lateinit var countUpTimer: CountUpTimer

    override val layoutId: Int = R.layout.fragment_game

    override fun viewModelClass() = GameViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvDigits)
        val btnUpdateModels = view.findViewById<AppCompatButton>(R.id.btnUpdateModels)
        val tvGameTime = view.findViewById<TextView>(R.id.tvGameTime)

        val gameAdapter = GameAdapter(object : ClickListener {
            override fun click(model: Model) {
                viewModel.tap(model.id)
            }
        })
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 9)
            adapter = gameAdapter
            itemAnimator = null
        }
        btnUpdateModels.setOnClickListener {
            viewModel.updateNumbers()
        }

        viewModel.initGame(GameFragmentArgs.fromBundle(requireArguments()).isNewGame)

        viewModel.startTime.observe(viewLifecycleOwner) { time ->
            startTimer(time, tvGameTime)
        }
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

    override fun onResume() {
        super.onResume()
        viewModel.initGameTime()
    }

    override fun onPause() {
        super.onPause()
        viewModel.prepareGameModelToSave()
        countUpTimer.stop()
    }

    private fun startTimer(time: Long, tvGameTime: TextView) {
        countUpTimer = object : CountUpTimer(time, 1000) {
            override fun onTick(elapsedTime: Long) {
                val hours = TimeUnit.MILLISECONDS.toHours(elapsedTime)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60
                tvGameTime.text = getString(R.string.time, hours, minutes, seconds)
                viewModel.gameTime.value = elapsedTime
            }
        }
        countUpTimer.start()
    }
}