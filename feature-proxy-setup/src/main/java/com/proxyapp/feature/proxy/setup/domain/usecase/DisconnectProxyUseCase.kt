package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.feature.proxy.setup.domain.model.ProxyConnectionProgress
import com.proxyapp.feature.proxy.setup.domain.repository.ProxySetupRepository
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DisconnectProxyUseCase @Inject constructor(
    private val repository: ProxySetupRepository
) {
    operator fun invoke(): Flow<ProxyConnectionProgress> {
        return repository.disconnect()
    }
}