package com.proxyapp.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state

    private val _effect = MutableSharedFlow<SettingsEffect>()
    val effect: SharedFlow<SettingsEffect> = _effect

    fun onIntent(intent: SettingsIntent) {
        when(intent) {
            SettingsIntent.SourceClicked -> sendEffect(SettingsEffect.NavigateToSource)
        }
    }

    fun sendEffect(effect: SettingsEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}