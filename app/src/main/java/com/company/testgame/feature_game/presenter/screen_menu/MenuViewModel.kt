package com.company.testgame.feature_game.presenter.screen_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.testgame.feature_game.domain.model.SkinRepository
import com.company.testgame.feature_game.domain.model.skin.Skin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val skinRepository: SkinRepository
): ViewModel() {

    private var _backgroundSkin = MutableStateFlow(Skin.Dummy)
    val backgroundSkin = _backgroundSkin.asStateFlow()

    init {
        viewModelScope.launch {
            skinRepository.getBackgroundSkins().collect { skins ->
                _backgroundSkin.value = skins.find { it.selected } ?: return@collect
            }
        }
    }
}