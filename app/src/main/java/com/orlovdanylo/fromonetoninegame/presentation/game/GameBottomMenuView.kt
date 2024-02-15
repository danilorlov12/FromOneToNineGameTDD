package com.orlovdanylo.fromonetoninegame.presentation.game

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.analytics.AnalyticsButton
import com.orlovdanylo.fromonetoninegame.analytics.logEventClickListener

class GameBottomMenuView : ConstraintLayout {

    var actions: GameBottomMenuActions? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.fragment_game_bottom_menu, this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val btnUndo = findViewById<AppCompatImageButton>(R.id.btnUndo)
        val btnRedo = findViewById<AppCompatImageButton>(R.id.btnRedo)
        val btnAddDigits = findViewById<AppCompatImageButton>(R.id.btnAddDigits)
        val btnTip = findViewById<AppCompatImageButton>(R.id.btnTip)

        btnAddDigits.isEnabled = false

        btnUndo.logEventClickListener(context, AnalyticsButton.UNDO) {
            actions?.undo()
        }

        btnRedo.logEventClickListener(context, AnalyticsButton.REDO) {
            actions?.redo()
        }

        btnAddDigits.logEventClickListener(context, AnalyticsButton.UPDATE_NUMBERS) {
            actions?.update()
        }

        btnTip.logEventClickListener(context, AnalyticsButton.TIP) {
            actions?.showTip()
        }
    }
}