package com.proxyapp.domain.repository

import com.proxyapp.domain.model.ProxyFilters
import kotlinx.coroutines.flow.Flow

interface FiltersRepository {
    fun observeFilters(): Flow<ProxyFilters>
    suspend fun saveFilters(filters: ProxyFilters)
}