package com.company.testgame.feature_game.presenter.screen_shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.testgame.feature_game.domain.model.ShopItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopItemsRepository: ShopItemsRepository
): ViewModel() {

    private var _state = MutableStateFlow(ShopUiState())
    val state = _state.asStateFlow()

    init {
        getAllSkins()
    }

    private fun getAllSkins() {
        shopItemsRepository.getRocketSkins()
            .onEach { skins ->
                _state.value = state.value.copy(
                    rocketSkinsList = skins
                )
            }
            .launchIn(viewModelScope)

        shopItemsRepository.getObstacleSkins()
            .onEach { skins ->
                _state.value = state.value.copy(
                    obstacleSkinsList = skins
                )
            }
            .launchIn(viewModelScope)

        shopItemsRepository.getBonusSkins()
            .onEach { skins ->
                _state.value = state.value.copy(
                    bonusSkinsList = skins
                )
            }
            .launchIn(viewModelScope)

        shopItemsRepository.getBackgroundSkins()
            .onEach { skins ->
                _state.value = state.value.copy(
                    backgroundSkinsList = skins
                )
            }
            .launchIn(viewModelScope)

    }
}