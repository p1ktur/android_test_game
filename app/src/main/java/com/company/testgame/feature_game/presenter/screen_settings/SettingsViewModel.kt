package com.company.testgame.feature_game.presenter.screen_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.testgame.feature_game.data.repository.AchievementRepository
import com.company.testgame.feature_game.data.repository.SettingsRepository
import com.company.testgame.feature_game.domain.model.Difficulty
import com.company.testgame.feature_game.domain.model.repository.SkinRepository
import com.company.testgame.feature_game.domain.model.achievement.PlayerAchievements
import com.company.testgame.feature_game.domain.model.skin.Skin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val skinRepository: SkinRepository,
    private val achievementRepository: AchievementRepository
): ViewModel() {

    private var _difficulty = MutableStateFlow<Difficulty>(Difficulty.Medium)
    val difficulty = _difficulty.asStateFlow()

    private var _achievements = MutableStateFlow(PlayerAchievements.Dummy)
    val achievements = _achievements.asStateFlow()

    private var _backgroundSkin = MutableStateFlow(Skin.Dummy)
    val backgroundSkin = _backgroundSkin.asStateFlow()

    init {
        getDifficulty()

        viewModelScope.launch {
            skinRepository.getBackgroundSkins().collect { skins ->
                _backgroundSkin.value = skins.find { it.selected } ?: return@collect
            }
        }

        getAchievements()
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

    fun setDifficulty(value: Difficulty) {
        _difficulty.value = value

        viewModelScope.launch {
            settingsRepository.saveDifficulty(difficulty.value)
        }
    }

    private fun getDifficulty() {
        viewModelScope.launch {
            _difficulty.value = settingsRepository.getDifficulty()
        }
    }
}