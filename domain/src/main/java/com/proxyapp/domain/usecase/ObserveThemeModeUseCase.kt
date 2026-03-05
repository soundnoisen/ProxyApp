package com.proxyapp.domain.usecase

import com.proxyapp.domain.model.ThemeMode
import com.proxyapp.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveThemeModeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    operator fun invoke(): Flow<ThemeMode> {
        return repository.observeThemeMode()
    }
}