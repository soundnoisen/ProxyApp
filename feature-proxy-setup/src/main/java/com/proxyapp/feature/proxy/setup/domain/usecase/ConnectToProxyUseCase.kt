package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.domain.model.Proxy
import com.proxyapp.feature.proxy.setup.domain.model.ProxyConnectionProgress
import com.proxyapp.feature.proxy.setup.domain.repository.ProxySetupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConnectToProxyUseCase @Inject constructor(
    private val repository: ProxySetupRepository
) {
    operator fun invoke(proxy: Proxy): Flow<ProxyConnectionProgress> {
        return repository.connect(proxy)
    }
}