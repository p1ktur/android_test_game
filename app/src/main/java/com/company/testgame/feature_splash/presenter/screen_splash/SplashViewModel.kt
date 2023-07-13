package com.company.testgame.feature_splash.presenter.screen_splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val randomInstance: Random
): ViewModel() {
    private val _loadingProgress = MutableStateFlow(0f)
    val loadingProgress = _loadingProgress.asStateFlow()

    init {
        val progressFlow = flow {
            do {
                delay(25L)
                val emittedValue = 0.01f + (randomInstance.nextInt(0, 10) / 100)
                emit(emittedValue)
            } while (loadingProgress.value <= 1f)
        }

        viewModelScope.launch {
            progressFlow.collect { value ->
                _loadingProgress.value += value
            }
        }
    }
}