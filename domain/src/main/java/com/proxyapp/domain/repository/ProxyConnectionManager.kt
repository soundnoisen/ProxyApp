package com.proxyapp.domain.repository

import com.proxyapp.domain.model.Proxy
import kotlinx.coroutines.flow.Flow

interface ProxyConnectionManager {
    val connectedProxy: Flow<Proxy?>
    suspend fun restoreConnection()
    suspend fun connect(proxy: Proxy)
    suspend fun disconnect()
}