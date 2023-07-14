package com.company.testgame.feature_game.domain.model.skin

import com.company.testgame.feature_game.domain.model.achievement.AchievementRequirement

sealed class SkinUnlockCondition(
    val id: Int,
    val mustBePurchased: Boolean,
    val buyAmount: Int?,
    val achievementRequirement: AchievementRequirement?
    ) {
    object Condition11: SkinUnlockCondition(
        11,
        false,
        null,
        AchievementRequirement.TotalScoreGained(10)
    )
    object Condition12: SkinUnlockCondition(
        12,
        false,
        null,
        AchievementRequirement.TotalDistanceFlied(1000)
    )
    object Condition13: SkinUnlockCondition(
        13,
        false,
        null,
        AchievementRequirement.TotalScoreGained(25)
    )
    object Condition21: SkinUnlockCondition(
        21,
        true,
        3,
        null
    )
    object Condition31: SkinUnlockCondition(
        31,
        false,
        null,
        AchievementRequirement.TotalDistanceFlied(2500)
    )
    object Condition41: SkinUnlockCondition(
        41,
        false,
        null,
        AchievementRequirement.TotalScoreGained(50)
    )
    object Condition42: SkinUnlockCondition(
        42,
        true,
        5,
        null
    )

    companion object {
        fun getConditionFromId(id: Int?): SkinUnlockCondition? {
            return when (id) {
                11 -> Condition11
                12 -> Condition12
                13 -> Condition13
                21 -> Condition21
                31 -> Condition31
                41 -> Condition41
                42 -> Condition42
                else -> null
            }
        }
    }
}
