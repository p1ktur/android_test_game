package com.company.testgame.feature_game.presenter.screen_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.testgame.feature_game.data.SettingsRepository
import com.company.testgame.feature_game.domain.model.Difficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {

    private var _difficulty = MutableStateFlow<Difficulty>(Difficulty.Medium)
    val difficulty = _difficulty.asStateFlow()

    init {
        getDifficulty()
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