package com.proxyapp.domain.repository

import com.proxyapp.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun observeThemeMode(): Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)
}