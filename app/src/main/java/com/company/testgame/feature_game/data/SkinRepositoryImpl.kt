package com.company.testgame.feature_game.data

import com.company.testgame.feature_game.data.achievement.AchievementDao
import com.company.testgame.feature_game.data.skin.SkinDao
import com.company.testgame.feature_game.domain.model.achievement.PlayerAchievements
import com.company.testgame.feature_game.domain.model.SkinRepository
import com.company.testgame.feature_game.domain.model.skin.Skin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SkinRepositoryImpl @Inject constructor(
    private val skinDao: SkinDao,
    private val achievementDao: AchievementDao
) : SkinRepository {

    override suspend fun getRocketSkins(): Flow<List<Skin>> {
        return skinDao.getRocketSkins()
    }

    override suspend fun getObstacleSkins(): Flow<List<Skin>> {
        return skinDao.getObstacleSkins()
    }

    override suspend fun getBonusSkins(): Flow<List<Skin>> {
        return skinDao.getBonusSkins()
    }

    override suspend fun getBackgroundSkins(): Flow<List<Skin>> {
        return skinDao.getBackgroundSkins()
    }

    override suspend fun insertSkin(skin: Skin) {
        skinDao.insertSkin(skin)
    }

    override suspend fun getAchievements(): Flow<List<PlayerAchievements>> {
        val achievementsFlow = achievementDao.getAchievements()
        return if (achievementsFlow.first().isEmpty()) {
            val newAchievement = PlayerAchievements(0, 0, 0, 0)
            updateAchievements(newAchievement)
            flow {
                emit(listOf(newAchievement))
            }
        } else achievementsFlow
    }

    override suspend fun updateAchievements(achievements: PlayerAchievements) {
        achievementDao.updateAchievements(achievements)
    }
}