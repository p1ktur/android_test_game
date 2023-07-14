package com.company.testgame.feature_game.domain.model.achievement

sealed class AchievementRequirement(val amount: Int, val conditionText: String) {
    class TotalScoreGained(amount: Int) : AchievementRequirement(
        amount,
        "To unlock this skin gain a total score of at least $amount"
    )
    class TotalDistanceFlied(amount: Int) : AchievementRequirement(
        amount,
        "To unlock this skin fly a total distance of at least $amount"
    )
}
