package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.repository.ProxyConnectionManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCurrentProxyUseCase @Inject constructor(
    private val manager: ProxyConnectionManager
){
    operator fun invoke(): Flow<Proxy?> {
        return manager.observeCurrentProxy()
    }
}

