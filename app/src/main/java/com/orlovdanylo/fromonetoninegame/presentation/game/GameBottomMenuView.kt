package com.orlovdanylo.fromonetoninegame.presentation.game

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.analytics.AnalyticsButton
import com.orlovdanylo.fromonetoninegame.analytics.logEventClickListener

class GameBottomMenuView : ConstraintLayout {

    private lateinit var viewModel: GameViewModel

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.fragment_game_bottom_menu, this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val viewLifecycleOwner = findViewTreeLifecycleOwner()!!

        viewModel = ViewModelProvider(findViewTreeViewModelStoreOwner()!!)[GameViewModel::class.java]

        val btnUndo = findViewById<AppCompatImageButton>(R.id.btnUndo)
        val btnRedo = findViewById<AppCompatImageButton>(R.id.btnRedo)
        val btnAddDigits = findViewById<AppCompatImageButton>(R.id.btnAddDigits)

        viewModel.gameModelsCount.observe(viewLifecycleOwner) { count ->
            btnAddDigits.isEnabled = count < 1000
        }

        viewModel.undoStack.observe(viewLifecycleOwner) { stack ->
            btnUndo.isEnabled = stack.isNotEmpty()
        }

        viewModel.redoStack.observe(viewLifecycleOwner) { stack ->
            btnRedo.isEnabled = stack.isNotEmpty()
        }

        btnUndo.logEventClickListener(context, AnalyticsButton.UNDO) {
            viewModel.undo(viewModel.gameModels.value!!, viewModel.removedNumbers)
        }

        btnRedo.logEventClickListener(context, AnalyticsButton.REDO) {
            viewModel.redo(viewModel.gameModels.value!!, viewModel.removedNumbers)
        }

        btnAddDigits.logEventClickListener(context, AnalyticsButton.ADD_DIGITS) {
            viewModel.updateNumbers()
        }
    }
}