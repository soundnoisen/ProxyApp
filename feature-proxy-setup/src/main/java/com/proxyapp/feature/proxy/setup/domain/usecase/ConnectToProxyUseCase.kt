package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.domain.Proxy
import com.proxyapp.feature.proxy.setup.domain.model.ProxyConnectionProgress
import com.proxyapp.feature.proxy.setup.domain.repository.ProxySetupRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConnectToProxyUseCase @Inject constructor(
    private val repository: ProxySetupRepository
) {
    operator fun invoke(proxy: Proxy): Flow<ProxyConnectionProgress> {
        return repository.connect(proxy)
    }
}