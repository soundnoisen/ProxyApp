package com.proxyapp.domain.repository

import com.proxyapp.domain.model.Proxy
import kotlinx.coroutines.flow.Flow

interface SavedProxyRepository {
    suspend fun add(proxy: Proxy)
    suspend fun remove(proxy: Proxy)
    fun observeSavedProxies(): Flow<List<Proxy>>
    suspend fun update(proxy: Proxy)
    suspend fun update(proxies: List<Proxy>)
    suspend fun exists(proxy: Proxy): Boolean
}