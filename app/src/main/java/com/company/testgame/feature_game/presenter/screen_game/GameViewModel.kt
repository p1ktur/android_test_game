package com.company.testgame.feature_game.presenter.screen_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.company.testgame.R
import com.company.testgame.feature_game.data.repository.AchievementRepository
import com.company.testgame.feature_game.data.repository.SettingsRepository
import com.company.testgame.feature_game.domain.model.Difficulty
import com.company.testgame.feature_game.domain.model.repository.SkinRepository
import com.company.testgame.feature_game.domain.model.achievement.PlayerAchievements
import com.company.testgame.feature_game.domain.model.skin.Skin
import com.company.testgame.feature_game.presenter.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val randomInstance: Random,
    private val achievementRepository: AchievementRepository,
    private val settingsRepository: SettingsRepository,
    private val skinRepository: SkinRepository
) : ViewModel() {

    private var gameStarted = false

    private lateinit var _difficulty: Difficulty

    private var _rocketAimDirection = MutableStateFlow(-1) //0 - left, 1 - right
    private var _rocketAimBias = MutableStateFlow(0f)

    private var _lives = MutableStateFlow(3)
    val lives = _lives.asStateFlow()

    private var _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private var _achievements = MutableStateFlow(PlayerAchievements.Dummy)
    val achievements = _achievements.asStateFlow()

    private var _eventFlow = MutableSharedFlow<GameUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val frameRate = 60
    private val oneFrameTime = 1000L / frameRate

    private var spawnInvincibilityFrames = 0

    private var _rocketSkin = MutableStateFlow(Skin.Dummy.copy(imageUrl = R.drawable.rocket_fly.toString()))
    val rocketSkin = _rocketSkin.asStateFlow()

    private var _obstacleSkin = MutableStateFlow(Skin.Dummy.copy(imageUrl = R.drawable.obstacle.toString()))
    val obstacleSkin = _obstacleSkin.asStateFlow()

    private var _bonusSkin = MutableStateFlow(Skin.Dummy.copy(imageUrl = R.drawable.star.toString()))
    val bonusSkin = _bonusSkin.asStateFlow()

    init {
        getDifficulty()
        getAchievements()
        getSelectedSkins()
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

                _achievements.value = _achievements.value.copy(
                    totalDistanceFlied = _achievements.value.totalDistanceFlied + 1
                )

                delay(oneFrameTime)
            }
        }
    }

    fun stopGame() {
        gameStarted = false

        viewModelScope.launch {
            _achievements.collect {
                achievementRepository.updateAchievements(_achievements.value)
            }
        }
    }

    fun stopGameWithNavigating(navController: NavController) {
        stopGame()

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
            _achievements.value = _achievements.value.copy(
                totalScoreGained = _achievements.value.totalScoreGained + 1
            )
        }
    }

    fun setLives(value: Int) {
        viewModelScope.launch {
            _lives.value = value
        }
    }

    fun addGems() {
        viewModelScope.launch {
            _achievements.value = _achievements.value.copy(
                playerGems = _achievements.value.playerGems + 1
            )
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

    private fun getAchievements() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                achievementRepository.getAchievements()
                    .first { records ->
                        if (records.isEmpty()) {
                            val newAchievement = PlayerAchievements(0, 0, 0, 0)
                            achievementRepository.updateAchievements(newAchievement)
                            getAchievements()
                        } else {
                            _achievements.value = records[0]
                        }
                        true
                    }
            }
        }
    }

    private fun getSelectedSkins() {
        viewModelScope.launch {
            skinRepository.getRocketSkins().collect { skins ->
                _rocketSkin.value = skins.find { it.selected } ?: return@collect
            }
        }
        viewModelScope.launch {
            skinRepository.getObstacleSkins().collect { skins ->
                _obstacleSkin.value = skins.find { it.selected } ?: return@collect
            }
        }
        viewModelScope.launch {
            skinRepository.getBonusSkins().collect { skins ->
                _bonusSkin.value = skins.find { it.selected } ?: return@collect
            }
        }
    }

    sealed class GameUiEvent {
        object SpawnRandomObject: GameUiEvent()
        object MoveAllObjects: GameUiEvent()
        data class MoveRocket(val direction: Int, val bias: Float): GameUiEvent()
    }
}