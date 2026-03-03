package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.domain.repository.ProxyConnectionManager
import javax.inject.Inject

class ConnectProxyUseCase @Inject constructor(
    private val repository: ProxyConnectionManager
) {
    operator fun invoke() = repository.connect()
}