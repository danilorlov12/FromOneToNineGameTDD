package com.orlovdanylo.fromonetoninegame.utils

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

fun View.pulseAnimation(): ObjectAnimator {
    val scaleXProperty = PropertyValuesHolder.ofFloat("scaleX", 1.1f)
    val scaleYProperty = PropertyValuesHolder.ofFloat("scaleY", 1.1f)

    return ObjectAnimator.ofPropertyValuesHolder(this, scaleXProperty, scaleYProperty).apply {
        duration = 750
        repeatCount = ObjectAnimator.INFINITE
        repeatMode = ObjectAnimator.REVERSE
        interpolator = AccelerateDecelerateInterpolator()
        start()
    }
}

fun View.cancelViewAnimation(objectAnimator: ObjectAnimator?) {
    objectAnimator?.cancel()
    scaleX = 1f
    scaleY = 1f
}