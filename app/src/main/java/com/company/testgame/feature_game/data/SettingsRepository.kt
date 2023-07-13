package com.company.testgame.feature_game.data

import com.company.testgame.feature_game.domain.model.Difficulty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val sharedPreferences: GameSharedPreferences
) {
    suspend fun saveDifficulty(difficulty: Difficulty) {
        sharedPreferences.saveDifficulty(difficulty)
    }

    suspend fun getDifficulty(): Difficulty {
        return sharedPreferences.getDifficulty()
    }
}