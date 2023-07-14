package com.company.testgame.feature_game.domain.model

sealed class ShopItem(val shopKey: String) {
    object RocketSkin: ShopItem("rocket")
    object ObstacleSkin: ShopItem("obstacle")
    object BonusSkin: ShopItem("bonus")
    object BackgroundSkin: ShopItem("background")
}
