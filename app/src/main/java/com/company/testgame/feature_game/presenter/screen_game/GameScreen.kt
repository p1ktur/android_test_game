package com.company.testgame.feature_game.presenter.screen_game

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.testgame.databinding.GameLayoutBinding
import com.company.testgame.feature_game.presenter.screen_game.gamelogic.GameScreenUiLogic
import kotlin.random.Random

@SuppressLint("InflateParams")
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    binding: GameLayoutBinding,
    navController: NavController,
    rootActivity: ComponentActivity,
    randomInstance: Random,
    gameViewModel: GameViewModel = hiltViewModel()
) {
    AndroidView(modifier = modifier, factory = {
        binding.root
    })

    val gameScreenUiLogic = GameScreenUiLogic(rootActivity, binding, gameViewModel, randomInstance, navController)

    LaunchedEffect(key1 = gameViewModel.eventFlow) {
        gameViewModel.eventFlow.collect { event ->
            when (event) {
                is GameViewModel.GameUiEvent.MoveRocket -> {
                    gameScreenUiLogic.moveRocket(event.direction, event.bias)
                }
                GameViewModel.GameUiEvent.MoveAllObjects -> {
                    gameScreenUiLogic.moveGameObjects()
                }
                GameViewModel.GameUiEvent.SpawnRandomObject -> {
                    gameScreenUiLogic.spawnGameObject()
                }
            }
        }
    }
}