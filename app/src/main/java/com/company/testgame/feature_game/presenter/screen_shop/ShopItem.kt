package com.company.testgame.feature_game.presenter.screen_shop

sealed class ShopItem(val shopKey: String) {
    object RocketSkin: ShopItem("rocket")
    object ObstacleSkin: ShopItem("obstacle")
    object BonusSkin: ShopItem("bonus")
    object BackgroundSkin: ShopItem("background")
}
