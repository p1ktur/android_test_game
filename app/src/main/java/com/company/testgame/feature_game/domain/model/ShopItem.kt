package com.company.testgame.feature_game.domain.model

//TODO get rid or change it
sealed class ShopItem(val shopKey: String) {
    object RocketSkin: ShopItem("rocket")
    object ObstacleSkin: ShopItem("obstacle")
    object BonusSkin: ShopItem("bonus")
    object BackgroundSkin: ShopItem("background")
}
