package com.company.testgame.feature_game.domain.model

import kotlinx.coroutines.flow.Flow

interface ShopItemsRepository {
    fun getRocketSkins(): Flow<List<Skin>>
    fun getObstacleSkins(): Flow<List<Skin>>
    fun getBonusSkins(): Flow<List<Skin>>
    fun getBackgroundSkins(): Flow<List<Skin>>
}