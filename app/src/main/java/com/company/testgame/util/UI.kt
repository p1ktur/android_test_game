package com.company.testgame.util

import android.view.View
import androidx.activity.ComponentActivity

fun ComponentActivity.hideSystemUI() {
    val decorView = this.window.decorView
    val uiOptions = decorView.systemUiVisibility
    val newUiOptions = uiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    decorView.systemUiVisibility = newUiOptions
}