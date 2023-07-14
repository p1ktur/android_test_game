package com.company.testgame.feature_game.domain.model.repository

import com.company.testgame.feature_game.domain.model.achievement.PlayerAchievements
import com.company.testgame.feature_game.domain.model.skin.Skin
import kotlinx.coroutines.flow.Flow

interface SkinRepository {
    suspend fun getRocketSkins(): Flow<List<Skin>>
    suspend fun getObstacleSkins(): Flow<List<Skin>>
    suspend fun getBonusSkins(): Flow<List<Skin>>
    suspend fun getBackgroundSkins(): Flow<List<Skin>>
    suspend fun insertSkin(skin: Skin)
    suspend fun getAchievements(): Flow<List<PlayerAchievements>>
    suspend fun updateAchievements(achievements: PlayerAchievements)
}