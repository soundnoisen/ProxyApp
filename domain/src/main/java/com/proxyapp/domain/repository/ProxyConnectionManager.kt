package com.proxyapp.domain.repository

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyConnectionStatus
import kotlinx.coroutines.flow.Flow

interface ProxyConnectionManager {
    suspend fun restoreConnection()
    suspend fun removeCurrentProxy()
    fun connect()
    fun disconnect()
    suspend fun setCurrentProxy(proxy: Proxy)
    fun observeCurrentProxy(): Flow<Proxy?>
    fun observeConnectionStatus(): Flow<ProxyConnectionStatus>
}