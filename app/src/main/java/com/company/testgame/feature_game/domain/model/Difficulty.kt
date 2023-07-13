package com.company.testgame.feature_game.domain.model

sealed class Difficulty {
    object Easy: Difficulty()
    object Medium: Difficulty()
    object Hard: Difficulty()
}
