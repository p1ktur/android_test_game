package com.company.testgame.feature_game.presenter.screen_game.gamelogic

import android.view.View

sealed class GameObject(val view: View, val bias: Float) {
    class Obstacle(view: View, bias: Float): GameObject(view, bias)
    class Bonus(view: View, bias: Float): GameObject(view, bias)
    class Gem(view: View, bias: Float): GameObject(view, bias)
}