package com.company.testgame.feature_game.data

import com.company.testgame.feature_game.data.achievement.AchievementDao
import com.company.testgame.feature_game.domain.model.Difficulty
import com.company.testgame.feature_game.domain.model.achievement.PlayerAchievements
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AchievementRepository @Inject constructor(
    private val achievementDao: AchievementDao
) {

    fun getAchievements(): Flow<List<PlayerAchievements>> {
        return achievementDao.getAchievements()
    }

    suspend fun updateAchievements(achievements: PlayerAchievements) {
        achievementDao.updateAchievements(achievements)
    }
}