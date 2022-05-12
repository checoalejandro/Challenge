package com.acosta.challenge.utils

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

private const val DURATION = 300L
private const val OFFSET = 300L

fun View.fadeOut() {
    val fadeOut = AlphaAnimation(1f, 0f)
    fadeOut.duration = DURATION
    fadeOut.startOffset = OFFSET
    fadeOut.interpolator = AccelerateInterpolator()
    fadeOut.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
        }

        override fun onAnimationEnd(p0: Animation?) {
            visibility = View.GONE
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }

    })
    startAnimation(fadeOut)
}