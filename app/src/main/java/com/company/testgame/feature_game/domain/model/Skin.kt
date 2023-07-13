package com.company.testgame.feature_game.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.company.testgame.feature_game.presenter.screen_shop.ShopItem

@Entity
data class Skin(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val category: ShopItem,
    val unlocked: Boolean = false,
    val imageUrl: String,
    val locallyStored: Boolean = false,
    val mustBePurchased: Boolean = false,
    val selected: Boolean = false
)
