package com.company.testgame.feature_game.data

import android.content.Context
import com.company.testgame.R
import com.company.testgame.feature_game.domain.model.Difficulty
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameSharedPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.settings_preferences),
        Context.MODE_PRIVATE
    )

    suspend fun saveDifficulty(difficulty: Difficulty) {
        with(sharedPreferences.edit()) {
            putInt(context.getString(R.string.difficulty_key), when (difficulty) {
                Difficulty.Easy -> 0
                Difficulty.Medium -> 1
                Difficulty.Hard -> 2
            })
            apply()
        }
    }

    suspend fun getDifficulty(): Difficulty {
        return when (sharedPreferences.getInt(context.getString(R.string.difficulty_key), 1)) {
            0 -> Difficulty.Easy
            1 -> Difficulty.Medium
            else -> Difficulty.Hard
        }
    }
}