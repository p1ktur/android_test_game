package com.company.testgame.feature_game.presenter.screen_shop

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.testgame.feature_game.data.repository.FakeSkinRepositoryImpl
import com.company.testgame.feature_game.domain.model.ShopItem
import com.company.testgame.feature_game.domain.model.achievement.PlayerAchievements
import com.company.testgame.feature_game.domain.model.repository.SkinRepository
import com.company.testgame.feature_game.domain.model.achievement.AchievementRequirement
import com.company.testgame.feature_game.domain.model.skin.Skin
import com.company.testgame.feature_game.domain.model.skin.SkinUnlockCondition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val skinRepository: SkinRepository
): ViewModel() {

    private var _achievements = MutableStateFlow(PlayerAchievements.Dummy)
    val achievements = _achievements.asStateFlow()

    private var _rocketSkins = MutableStateFlow<List<Skin>>(emptyList())
    private var _obstacleSkins = MutableStateFlow<List<Skin>>(emptyList())
    private var _bonusSkins = MutableStateFlow<List<Skin>>(emptyList())
    private var _backgroundSkins = MutableStateFlow<List<Skin>>(emptyList())

    private var _backgroundSkin = MutableStateFlow(Skin.Dummy)
    val backgroundSkin = _backgroundSkin.asStateFlow()

    val state = combine(_rocketSkins, _obstacleSkins, _bonusSkins, _backgroundSkins, _achievements) {
            rocketSkins, obstacleSkins, bonusSkins, backgroundSkins, achievements ->
        ShopUiState(
            rocketSkins.mapSkinsWithAchievements(),
            obstacleSkins.mapSkinsWithAchievements(),
            bonusSkins.mapSkinsWithAchievements(),
            backgroundSkins.mapSkinsWithAchievements()
        )
    }

    private var _shownSkin = MutableStateFlow(Skin.Dummy)
    val shownSkin = _shownSkin.asStateFlow()

    private var _purchaseDialogShown = MutableStateFlow(false)
    val purchaseDialogShown = _purchaseDialogShown.asStateFlow()

    private var _requirementDialogShown = MutableStateFlow(false)
    val requirementDialogShown = _requirementDialogShown.asStateFlow()

    init {
        getAllSkins()
        getAchievements()
    }

    private suspend fun List<Skin>.mapSkinsWithAchievements(): List<Skin> {
        return this.map { skin ->
            val unlocked = skin.unlocked
            val condition = SkinUnlockCondition.getConditionFromId(skin.unlockConditionId)
            val conditionRequirement = condition?.achievementRequirement
            val newSkin = skin.copy(
                unlocked = if (condition == null) true else if (condition.mustBePurchased) unlocked else
                    when (conditionRequirement) {
                    is AchievementRequirement.TotalDistanceFlied -> _achievements.value.totalDistanceFlied >= conditionRequirement.amount
                    is AchievementRequirement.TotalScoreGained -> _achievements.value.totalScoreGained >= conditionRequirement.amount
                    null -> unlocked
                }
            )
            if (unlocked != newSkin.unlocked) {
                skinRepository.insertSkin(newSkin)
            }
            newSkin
        }
    }

    fun selectSkin(skin: Skin) {
        if (skin.selected) return

        if (skin.unlocked) {
            viewModelScope.launch {
                when (skin.category) {
                    ShopItem.RocketSkin.shopKey -> {
                        _rocketSkins.value = _rocketSkins.value.map { s ->
                            if (s.selected) {
                                val newSkin = s.copy(selected = false)
                                skinRepository.insertSkin(newSkin)
                                newSkin
                            } else if (s.id == skin.id) {
                                val newSkin = skin.copy(selected = true)
                                skinRepository.insertSkin(newSkin)
                                newSkin
                            } else s
                        }
                    }
                    ShopItem.ObstacleSkin.shopKey -> {
                        _obstacleSkins.value = _obstacleSkins.value.map { s ->
                            if (s.selected) {
                                val newSkin = s.copy(selected = false)
                                skinRepository.insertSkin(newSkin)
                                newSkin
                            } else if (s.id == skin.id) {
                                val newSkin = skin.copy(selected = true)
                                skinRepository.insertSkin(newSkin)
                                newSkin
                            } else s
                        }
                    }
                    ShopItem.BonusSkin.shopKey -> {
                        _bonusSkins.value = _bonusSkins.value.map { s ->
                            if (s.selected) {
                                val newSkin = s.copy(selected = false)
                                skinRepository.insertSkin(newSkin)
                                newSkin
                            } else if (s.id == skin.id) {
                                val newSkin = skin.copy(selected = true)
                                skinRepository.insertSkin(newSkin)
                                newSkin
                            } else s
                        }
                    }
                    ShopItem.BackgroundSkin.shopKey -> {
                        _backgroundSkins.value = _backgroundSkins.value.map { s ->
                            if (s.selected) {
                                val newSkin = s.copy(selected = false)
                                skinRepository.insertSkin(newSkin)
                                newSkin
                            } else if (s.id == skin.id) {
                                val newSkin = skin.copy(selected = true)
                                skinRepository.insertSkin(newSkin)
                                newSkin
                            } else s
                        }

                    }
                }

                skinRepository.insertSkin(skin.copy(selected = true))
            }
        } else {
            Log.d("TAG", "cond ${SkinUnlockCondition.getConditionFromId(skin.unlockConditionId)}")
            val condition = SkinUnlockCondition.getConditionFromId(skin.unlockConditionId) ?: return
            viewModelScope.launch {
                _shownSkin.value = skin

                if (condition.mustBePurchased) {
                    _purchaseDialogShown.value = true
                } else {
                    _requirementDialogShown.value = true
                }
            }
        }
    }

    fun buySkin(skin: Skin, cost: Int) {
        viewModelScope.launch {
            when (skin.category) {
                ShopItem.RocketSkin.shopKey -> {
                    _rocketSkins.value = _rocketSkins.value.map { s ->
                        if (s.selected) {
                            val newSkin = s.copy(selected = false)
                            skinRepository.insertSkin(newSkin)
                            newSkin
                        } else if (s.id == skin.id) {
                            val newSkin = skin.copy(
                                selected = true,
                                unlocked = true
                            )
                            skinRepository.insertSkin(newSkin)
                            newSkin
                        } else s
                    }
                }
                ShopItem.ObstacleSkin.shopKey -> {
                    _obstacleSkins.value = _obstacleSkins.value.map { s ->
                        if (s.selected) {
                            val newSkin = s.copy(selected = false)
                            skinRepository.insertSkin(newSkin)
                            newSkin
                        } else if (s.id == skin.id) {
                            val newSkin = skin.copy(
                                selected = true,
                                unlocked = true
                            )
                            skinRepository.insertSkin(newSkin)
                            newSkin
                        } else s
                    }
                }
                ShopItem.BonusSkin.shopKey -> {
                    _bonusSkins.value = _bonusSkins.value.map { s ->
                        if (s.selected) {
                            val newSkin = s.copy(selected = false)
                            skinRepository.insertSkin(newSkin)
                            newSkin
                        } else if (s.id == skin.id) {
                            val newSkin = skin.copy(
                                selected = true,
                                unlocked = true
                            )
                            skinRepository.insertSkin(newSkin)
                            newSkin
                        } else s
                    }
                }
                ShopItem.BackgroundSkin.shopKey -> {
                    _backgroundSkins.value = _backgroundSkins.value.map { s ->
                        if (s.selected) {
                            val newSkin = s.copy(selected = false)
                            skinRepository.insertSkin(newSkin)
                            newSkin
                        } else if (s.id == skin.id) {
                            val newSkin = skin.copy(
                                selected = true,
                                unlocked = true
                            )
                            skinRepository.insertSkin(newSkin)
                            newSkin
                        } else s
                    }
                }
            }

            _achievements.value = _achievements.value.copy(
                playerGems = _achievements.value.playerGems - cost
            )
            skinRepository.updateAchievements(_achievements.value)
        }
    }

    fun hidePurchaseDialog() {
        _purchaseDialogShown.value = false
    }

    fun hideRequirementDialog() {
        _requirementDialogShown.value = false
    }

    private fun getAllSkins() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getRocketSkins()
                getObstacleSkins()
                getBonusSkins()
                getBackgroundSkins()
                getShopBackgroundSkin()
            }
        }
    }

    private suspend fun getRocketSkins() {
        skinRepository.getRocketSkins()
            .first { skins ->
                if (skins.isEmpty()) {
                    val newSkins = FakeSkinRepositoryImpl().getRocketSkins().first()
                    newSkins.forEach { skin ->
                        skinRepository.insertSkin(skin)
                    }
                    getRocketSkins()
                } else {
                    _rocketSkins.value = skins
                }
                true
            }
    }

    private suspend fun getObstacleSkins() {
        skinRepository.getObstacleSkins()
            .first { skins ->
                if (skins.isEmpty()) {
                    val newSkins = FakeSkinRepositoryImpl().getObstacleSkins().first()
                    newSkins.forEach { skin ->
                        skinRepository.insertSkin(skin)
                    }
                    getObstacleSkins()
                } else {
                    _obstacleSkins.value = skins
                }
                true
            }
    }

    private suspend fun getBonusSkins() {
        skinRepository.getBonusSkins()
            .first { skins ->
                if (skins.isEmpty()) {
                    val newSkins = FakeSkinRepositoryImpl().getBonusSkins().first()
                    newSkins.forEach { skin ->
                        skinRepository.insertSkin(skin)
                    }
                    getBonusSkins()
                } else {
                    _bonusSkins.value = skins
                }
                true
            }
    }

    private suspend fun getBackgroundSkins() {
        skinRepository.getBackgroundSkins()
            .first { skins ->
                if (skins.isEmpty()) {
                    val newSkins = FakeSkinRepositoryImpl().getBackgroundSkins().first()
                    newSkins.forEach { skin ->
                        skinRepository.insertSkin(skin)
                    }
                    getBackgroundSkins()
                } else {
                    _backgroundSkins.value = skins
                }
                true
            }
    }

    private suspend fun getShopBackgroundSkin() {
        _backgroundSkins.collect { skins ->
            _backgroundSkin.value = skins.find { it.selected } ?: return@collect
        }
    }

    private fun getAchievements() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                skinRepository.getAchievements()
                    .first { records ->
                        if (records.isEmpty()) {
                            val newAchievement = PlayerAchievements(0, 0, 0, 0)
                            skinRepository.updateAchievements(newAchievement)
                            getAchievements()
                        } else {
                            _achievements.value = records[0]
                        }
                        true
                    }
            }
        }
    }
}