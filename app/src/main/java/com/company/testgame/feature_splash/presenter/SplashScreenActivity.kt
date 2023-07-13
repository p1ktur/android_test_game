package com.company.testgame.feature_splash.presenter

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.company.testgame.R
import com.company.testgame.feature_game.presenter.MainActivity
import com.company.testgame.feature_splash.presenter.screen_splash.SplashScreen
import com.company.testgame.ui.theme.LightBlue
import com.company.testgame.ui.theme.TestGameTheme
import com.company.testgame.util.hideSystemUI
import dagger.hilt.android.AndroidEntryPoint

//Decided to use activity because splash screen api doesn't provide
//way to install custom loading progressbar
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        setContent {
            TestGameTheme {
                SplashScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(LightBlue)
                ) {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finishAfterTransition()
                }
            }
        }
    }
}