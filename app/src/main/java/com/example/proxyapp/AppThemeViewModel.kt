package com.example.proxyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proxyapp.domain.model.ThemeMode
import com.proxyapp.domain.usecase.ObserveThemeModeUseCase
import com.proxyapp.domain.usecase.SetThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppThemeViewModel @Inject constructor(
    observeThemeMode: ObserveThemeModeUseCase,
    private val setThemeMode: SetThemeModeUseCase
): ViewModel() {

    val themeMode: StateFlow<ThemeMode> = observeThemeMode().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)

    fun changeTheme(themeMode: ThemeMode) {
        viewModelScope.launch {
            setThemeMode(themeMode)
        }
    }
}