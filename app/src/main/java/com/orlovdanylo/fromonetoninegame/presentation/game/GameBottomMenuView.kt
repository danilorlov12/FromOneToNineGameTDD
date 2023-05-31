package com.orlovdanylo.fromonetoninegame.presentation.game

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.orlovdanylo.fromonetoninegame.R

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

        val btnUndo: AppCompatImageButton = findViewById(R.id.btnUndo)
        val btnRedo: AppCompatImageButton = findViewById(R.id.btnRedo)
        val btnAddDigits: AppCompatImageButton = findViewById(R.id.btnAddDigits)

        viewModel.gameModelsCount.observe(viewLifecycleOwner) { count ->
            btnAddDigits.isEnabled = count < 1000
        }
        viewModel.undoStack.observe(viewLifecycleOwner) { stack ->
            btnUndo.isEnabled = stack.isNotEmpty()
        }
        viewModel.redoStack.observe(viewLifecycleOwner) { stack ->
            btnRedo.isEnabled = stack.isNotEmpty()
        }
        btnUndo.setOnClickListener {
            viewModel.undo(viewModel.gameModels.value!!)
        }
        btnRedo.setOnClickListener {
            viewModel.redo(viewModel.gameModels.value!!)
        }
        btnAddDigits.setOnClickListener {
            viewModel.updateNumbers()
        }
    }
}