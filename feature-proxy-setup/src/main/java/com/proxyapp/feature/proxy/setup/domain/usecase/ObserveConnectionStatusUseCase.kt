package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.domain.model.ProxyConnectionStatus
import com.proxyapp.domain.repository.ProxyConnectionManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveConnectionStatusUseCase @Inject constructor(
    private val manager: ProxyConnectionManager
){
    operator fun invoke(): Flow<ProxyConnectionStatus> {
        return manager.observeConnectionStatus()
    }
}