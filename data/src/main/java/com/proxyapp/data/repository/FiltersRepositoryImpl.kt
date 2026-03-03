package com.proxyapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.proxyapp.data.di.FiltersDataStore
import com.proxyapp.domain.model.ProxyFilters
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.domain.model.SpeedRange
import com.proxyapp.domain.repository.FiltersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FiltersRepositoryImpl @Inject constructor(
    @FiltersDataStore private val dataStore: DataStore<Preferences>
): FiltersRepository {

    override fun observeFilters(): Flow<ProxyFilters> {
        return dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { prefs ->
                ProxyFilters(
                    activeOnly = prefs[Keys.ACTIVE_ONLY] ?: false,
                    telegramFilter = prefs[Keys.TELEGRAM_FILTER] ?: false,
                    speedRange = SpeedRange(
                        minSpeed = prefs[Keys.SPEED_START] ?: 0f,
                        maxSpeed = prefs[Keys.SPEED_END] ?: 60f
                    ),
                    protocols = prefs[Keys.PROTOCOLS]?.mapNotNull { name ->
                        runCatching { ProxyProtocol.valueOf(name) }.getOrNull()
                    }?.sortedBy { it.name } ?: emptyList(),
                    countriesIso = prefs[Keys.COUNTRIES]?.toList()?.sorted() ?: emptyList()
                )
            }
    }

    override suspend fun saveFilters(filters: ProxyFilters) {
        dataStore.edit { prefs ->
            prefs[Keys.ACTIVE_ONLY] = filters.activeOnly
            prefs[Keys.TELEGRAM_FILTER] = filters.telegramFilter
            prefs[Keys.SPEED_START] = filters.speedRange.minSpeed
            prefs[Keys.SPEED_END] = filters.speedRange.maxSpeed
            prefs[Keys.PROTOCOLS] = filters.protocols.map { it.name }.toSet()
            prefs[Keys.COUNTRIES] = filters.countriesIso.toSet()
        }
    }

    private object Keys {
        val ACTIVE_ONLY = booleanPreferencesKey("active_only")
        val TELEGRAM_FILTER = booleanPreferencesKey("telegram_filter")
        val SPEED_START = floatPreferencesKey("speed_start")
        val SPEED_END = floatPreferencesKey("speed_end")
        val PROTOCOLS = stringSetPreferencesKey("protocols")
        val COUNTRIES = stringSetPreferencesKey("countries")
    }
}