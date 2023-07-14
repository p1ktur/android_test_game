package com.company.testgame.feature_game.domain.model.achievement

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ach_table")
data class PlayerAchievements(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val totalDistanceFlied: Int,
    val totalScoreGained: Int,
    val playerGems: Int
) {
    companion object {
        val Dummy = PlayerAchievements(
            id = -1,
            totalDistanceFlied = 0,
            totalScoreGained = 0,
            playerGems = 0
        )
    }
}
