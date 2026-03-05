package com.proxyapp.domain.usecase

import com.proxyapp.domain.model.ThemeMode
import com.proxyapp.domain.repository.ThemeRepository
import javax.inject.Inject

class SetThemeModeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke(themeMode: ThemeMode) {
        repository.setThemeMode(themeMode)
    }
}