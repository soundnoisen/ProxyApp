package com.proxyapp.domain.repository

import com.proxyapp.domain.model.LoadProgress
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyFilters
import com.proxyapp.domain.model.ProxyProtocol
import kotlinx.coroutines.flow.Flow

interface ProxyRepository {
    fun observeProxies(): Flow<List<Proxy>>
    fun refresh(filters: ProxyFilters): Flow<LoadProgress>
    fun loadNextPage(filters: ProxyFilters): Flow<LoadProgress>
    suspend fun checkProxy(proxy: Proxy): Proxy
    suspend fun checkProxies(protocol: ProxyProtocol,proxies: List<Proxy>): List<Proxy>
}