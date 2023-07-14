package com.company.testgame.feature_game.data.achievement

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.company.testgame.feature_game.domain.model.achievement.PlayerAchievements
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {

    @Query("SELECT * FROM ach_table")
    fun getAchievements(): Flow<List<PlayerAchievements>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAchievements(achievements: PlayerAchievements)
}