package com.company.testgame.util

import android.view.View

//Didn't want to create xml files for animations to create compose-like approach to xml views
//a.k.a. just wanted to implement such approach
fun View.click() {
    animate().apply {
        duration = 80
        scaleX(0.9f)
        scaleY(0.9f)
    }.withEndAction {
        animate().apply {
            duration = 80
            scaleX(1f)
            scaleY(1f)
        }
    }
}

fun View.fadeIn() {
    visibility = View.VISIBLE
    alpha = 0.0f
    scaleX = 0.8f
    scaleY = 0.8f
    translationY = -height.toFloat()

    animate().apply {
        duration = 300
        translationYBy(height.toFloat())
        scaleX(1.0f)
        scaleY(1.0f)
        alpha(1.0f)
    }.withEndAction {
        alpha = 1.0f
        scaleX = 1.0f
        scaleY = 1.0f
    }
}

fun View.fadeOut() {
    animate().apply {
        duration = 300
        translationYBy(height.toFloat())
        scaleX(0.8f)
        scaleY(0.8f)
        alpha(0.0f)
    }.withEndAction {
        visibility = View.GONE
        alpha = 0.0f
    }
}

fun View.float(repeatCount: Int = 10000, scaleDirection: Int = 1) {
    if (repeatCount == 0) return
    animate().apply {
        scaleXBy(0.15f * scaleDirection)
        scaleYBy(0.15f * scaleDirection)
        duration = 800
    }.withEndAction {
        float(repeatCount - 1, scaleDirection * -1)
    }
}

fun View.starsFadeIn() {
    animate().apply {
        duration = 3000
        alpha(1.0f)
    }
}

fun View.moveDown() {
    animate().apply {
        duration = 800
        translationYBy(500f)
    }
}