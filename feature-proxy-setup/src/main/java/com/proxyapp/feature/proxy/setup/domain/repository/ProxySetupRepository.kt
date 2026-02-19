package com.proxyapp.feature.proxy.setup.domain.repository

import com.proxyapp.domain.Proxy
import com.proxyapp.domain.ProxyProtocol
import com.proxyapp.feature.proxy.setup.domain.model.ProxyConnectionProgress
import kotlinx.coroutines.flow.Flow

interface ProxySetupRepository {
    fun connect(proxy: Proxy): Flow<ProxyConnectionProgress>
    fun disconnect(): Flow<ProxyConnectionProgress>
    fun addProxy(proxy: String, port: String, protocol: ProxyProtocol, username: String?, password: String?, secret: String?)
}