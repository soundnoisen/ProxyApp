package com.proxyapp.feature.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proxyapp.feature.splash.domain.RestoreConnectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val restoreConnection: RestoreConnectionUseCase
): ViewModel() {

    private val _showSplash = MutableStateFlow(true)
    val showSplash: StateFlow<Boolean> = _showSplash

    init {
        viewModelScope.launch {
            restoreConnection()
        }
    }

    fun finishSplash() {
        _showSplash.value = false
    }
}