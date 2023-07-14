package com.company.testgame.feature_game.presenter.screen_shop

import com.company.testgame.feature_game.domain.model.skin.Skin

data class ShopUiState(
    val rocketSkinsList: List<Skin> = emptyList(),
    val obstacleSkinsList: List<Skin> = emptyList(),
    val bonusSkinsList: List<Skin> = emptyList(),
    val backgroundSkinsList: List<Skin> = emptyList()
)