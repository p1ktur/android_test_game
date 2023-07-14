package com.company.testgame.feature_game.presenter.screen_game

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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

    val rocketSkin = gameViewModel.rocketSkin.collectAsState()
    val obstacleSkin = gameViewModel.obstacleSkin.collectAsState()
    val bonusSkin = gameViewModel.bonusSkin.collectAsState()

    LaunchedEffect(key1 = rocketSkin.value) {
        gameScreenUiLogic.setRocketSkin(rocketSkin.value)
    }

    LaunchedEffect(key1 = obstacleSkin.value) {
        gameScreenUiLogic.setObstacleSkin(obstacleSkin.value)
    }

    LaunchedEffect(key1 = bonusSkin.value) {
        Log.d("TAG", "STAR ${bonusSkin.value}")
        gameScreenUiLogic.setBonusSkin(bonusSkin.value)
    }

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