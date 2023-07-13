package com.company.testgame.feature_game.presenter.util

sealed class Screen(val route: String) {
    object MenuScreen: Screen("menu_screen")
    object GameScreen: Screen("game_screen")
    object ResultScreen: Screen("result_screen")
    object ShopScreen: Screen("shop_screen")
    object SettingsScreen: Screen("settings_screen")
}
