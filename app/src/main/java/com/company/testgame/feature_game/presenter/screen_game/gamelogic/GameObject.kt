package com.company.testgame.feature_game.presenter.screen_game.gamelogic

import android.view.View

sealed class GameObject(open val view: View, open val bias: Float) {
    class Obstacle(view: View, bias: Float): GameObject(view, bias)
    class Bonus(view: View, bias: Float): GameObject(view, bias)
}