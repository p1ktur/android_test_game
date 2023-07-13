package com.company.testgame.feature_game.presenter.screen_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.company.testgame.feature_game.data.SettingsRepository
import com.company.testgame.feature_game.domain.model.Difficulty
import com.company.testgame.feature_game.presenter.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val randomInstance: Random,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private var gameStarted = false

    private lateinit var _difficulty: Difficulty

    private var _rocketAimDirection = MutableStateFlow(-1) //0 - left, 1 - right
    private var _rocketAimBias = MutableStateFlow(0f)

    private var _eventFlow = MutableSharedFlow<GameUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var _lives = MutableStateFlow(3)
    val lives = _lives.asStateFlow()

    private var _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    val frameRate = 60
    private val oneFrameTime = 1000L / frameRate

    private var spawnInvincibilityFrames = 0

    init {
        getDifficulty()
    }

    fun startGame() {
        viewModelScope.launch {
            gameStarted = true

            while (gameStarted) {
                if (lives.value <= 0) break

                if (randomInstance.nextInt(1, 81) < 3 && spawnInvincibilityFrames <= 0) {
                    _eventFlow.emit(GameUiEvent.SpawnRandomObject)
                    spawnInvincibilityFrames = frameRate * 2 / (when (_difficulty) {
                        Difficulty.Hard -> 2f
                        Difficulty.Medium -> 1.5f
                        Difficulty.Easy -> 1f
                    }).roundToInt()
                } else {
                    spawnInvincibilityFrames--
                }

                _eventFlow.emit(GameUiEvent.MoveAllObjects)

                if (_rocketAimDirection.value > -1) {
                    _eventFlow.emit(GameUiEvent.MoveRocket(_rocketAimDirection.value, _rocketAimBias.value))
                }

                delay(oneFrameTime)
            }
        }
    }

    fun stopGame(navController: NavController) {
        gameStarted = false

        navController.popBackStack()
        navController.navigate(Screen.ResultScreen.route +
                "?score=${score.value}")
    }

    fun setRocketAimDirection(value: Int) {
        viewModelScope.launch {
            _rocketAimDirection.value = value
        }
    }

    fun setRocketAimBias(value: Float) {
        viewModelScope.launch {
            _rocketAimBias.value = value
        }
    }

    fun setScore(value: Int) {
        viewModelScope.launch {
            _score.value = value
        }
    }

    fun setLives(value: Int) {
        viewModelScope.launch {
            _lives.value = value
        }
    }

    private fun getDifficulty() {
        viewModelScope.launch {
            _difficulty = settingsRepository.getDifficulty()

            when (_difficulty) {
                Difficulty.Easy -> _lives.value = 5
                Difficulty.Medium -> _lives.value = 3
                Difficulty.Hard -> _lives.value = 1
            }
        }
    }

    sealed class GameUiEvent {
        object SpawnRandomObject: GameUiEvent()
        object MoveAllObjects: GameUiEvent()
        data class MoveRocket(val direction: Int, val bias: Float): GameUiEvent()
    }
}