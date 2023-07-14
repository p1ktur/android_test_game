package com.company.testgame.feature_game.data.achievement

import androidx.room.Database
import androidx.room.RoomDatabase
import com.company.testgame.feature_game.domain.model.achievement.PlayerAchievements

@Database(
    entities = [PlayerAchievements::class],
    version = 1,
    exportSchema = false
)
abstract class AchievementDatabase : RoomDatabase() {

    abstract val achievementDao: AchievementDao

    companion object {
        const val ACHIEVEMENT_DB_NAME = "ACHIEVEMENT_DB_NAME"
    }
}