package com.company.testgame.feature_game.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.company.testgame.databinding.GameLayoutBinding
import com.company.testgame.feature_game.presenter.screen_game.GameScreen
import com.company.testgame.feature_game.presenter.screen_menu.MenuScreen
import com.company.testgame.feature_game.presenter.screen_result.ResultScreen
import com.company.testgame.feature_game.presenter.screen_settings.SettingsScreen
import com.company.testgame.feature_game.presenter.screen_shop.ShopScreen
import com.company.testgame.feature_game.presenter.util.Screen
import com.company.testgame.ui.theme.TestGameTheme
import com.company.testgame.util.hideSystemUI
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var gameScreenBinding: GameLayoutBinding

    @Inject
    lateinit var randomInstance: Random

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        setContent {
            TestGameTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.MenuScreen.route) {
                    composable(route = Screen.MenuScreen.route) {
                        MenuScreen(
                            modifier = Modifier.fillMaxSize(),
                            this@MainActivity,
                            navController = navController
                        )
                    }
                    composable(route = Screen.GameScreen.route) {
                        gameScreenBinding = GameLayoutBinding.inflate(layoutInflater)
                        GameScreen(
                            modifier = Modifier.fillMaxSize(),
                            binding = gameScreenBinding,
                            navController = navController,
                            rootActivity = this@MainActivity,
                            randomInstance = randomInstance
                        )
                    }
                    composable(
                        route = Screen.ResultScreen.route + "?score={score}",
                        arguments = listOf(
                            navArgument("score") {
                                type = NavType.IntType
                                defaultValue = 0
                            }
                        )
                    ) {
                        ResultScreen(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController,
                            score = it.arguments?.getInt("score") ?: 0
                        )
                    }
                    composable(route = Screen.ShopScreen.route) {
                        ShopScreen(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController
                        )
                    }
                    composable(route = Screen.SettingsScreen.route) {
                        SettingsScreen(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}