package com.orlovdanylo.fromonetoninegame.presentation.game

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.base.BaseFragment
import com.orlovdanylo.fromonetoninegame.presentation.alert_dialog.CustomAlertDialog
import com.orlovdanylo.fromonetoninegame.presentation.game.adapter.ClickListener
import com.orlovdanylo.fromonetoninegame.presentation.game.adapter.GameAdapter
import com.orlovdanylo.fromonetoninegame.utils.CountUpTimer
import com.orlovdanylo.fromonetoninegame.presentation.game.models.GameModel
import java.util.concurrent.TimeUnit

class GameFragment : BaseFragment<GameViewModel>() {

    private lateinit var countUpTimer: CountUpTimer

    override val layoutId: Int = R.layout.fragment_game

    override fun viewModelClass() = GameViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvDigits)
        val tvGameTime = view.findViewById<TextView>(R.id.tvGameTime)

        val gameAdapter = GameAdapter(object : ClickListener {
            override fun click(model: GameModel) {
                viewModel.tap(model.id)
            }
        })
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 9)
            adapter = gameAdapter
            itemAnimator = null
        }
        tvGameTime.setOnClickListener {
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
        viewModel.updatedPair.observe(viewLifecycleOwner) { pair ->
            if (pair != null) {
                gameAdapter.notifyItemChanged(pair.first)
                gameAdapter.notifyItemChanged(pair.second)
                viewModel.selectedModel.value = null
            }
        }
        viewModel.selectedModel.observe(viewLifecycleOwner) { model ->
            val item = if (model != null) {
                val item = viewModel.gameModels.value!!.first { it.id == model.id }
                item.isSelected = true
                item
            } else {
                val item = viewModel.gameModels.value!!.firstOrNull { it.isSelected } ?: return@observe
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
        viewModel.isGameFinished.observe(viewLifecycleOwner) { isGameFinished ->
            if (isGameFinished) {
                CustomAlertDialog(
                    context = requireContext(),
                    title = getString(R.string.congratulations_you_won),
                    positiveButtonText = getString(R.string.okay)
                ) { navController.navigateUp() }
                    .create()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initGameTime()
    }

    override fun onPause() {
        super.onPause()
        viewModel.checkCurrentGame()
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