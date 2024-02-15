package com.orlovdanylo.fromonetoninegame.presentation.core

interface ClickListener<T> {
    fun click(model: T)
}