package com.proxyapp.domain.repository

import com.proxyapp.domain.model.LoadProgress
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyFilters
import com.proxyapp.domain.model.SaveResult
import kotlinx.coroutines.flow.Flow

interface ProxyRepository {
    fun observeProxies(): Flow<List<Proxy>>
    suspend fun refresh(filters: ProxyFilters): Flow<LoadProgress>
    suspend fun loadNextPage(filters: ProxyFilters): Flow<LoadProgress>
    suspend fun save(proxy: Proxy): Flow<SaveResult>
}