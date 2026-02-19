package com.proxyapp.feature.proxy.setup.data.repository

import com.proxyapp.domain.Proxy
import com.proxyapp.domain.ProxyProtocol
import com.proxyapp.feature.proxy.setup.domain.model.ProxyConnectionProgress
import com.proxyapp.feature.proxy.setup.domain.repository.ProxySetupRepository
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProxySetupRepositoryImpl @Inject constructor(): ProxySetupRepository {

    override fun connect(proxy: Proxy): Flow<ProxyConnectionProgress> = flow {
        emit(ProxyConnectionProgress.Connecting)
        delay(1000)
        emit(ProxyConnectionProgress.Success)
    }

    override fun disconnect(): Flow<ProxyConnectionProgress> = flow {
        emit(ProxyConnectionProgress.Disconnecting)
        delay(500)
        emit(ProxyConnectionProgress.Success)
    }

    override fun addProxy(
        proxy: String,
        port: String,
        protocol: ProxyProtocol,
        username: String?,
        password: String?,
        secret: String?
    ) { }
}