package com.proxyapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.proxyapp.data.di.ThemeDataStore
import com.proxyapp.domain.model.ThemeMode
import com.proxyapp.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    @ThemeDataStore private val dataStore: DataStore<Preferences>
): ThemeRepository {

    private val THEME_KEY = stringPreferencesKey("theme_mode")

    override fun observeThemeMode(): Flow<ThemeMode> =
        dataStore.data.map { prefs ->
            ThemeMode.valueOf(prefs[THEME_KEY] ?: ThemeMode.SYSTEM.name)
        }

    override suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.edit { it[THEME_KEY] = mode.name }
    }
}