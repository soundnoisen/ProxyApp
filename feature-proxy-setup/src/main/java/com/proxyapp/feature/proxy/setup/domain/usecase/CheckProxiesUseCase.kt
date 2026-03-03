package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.domain.repository.ProxyRepository
import javax.inject.Inject

class CheckProxiesUseCase @Inject constructor(
    private val repository: ProxyRepository,
) {
    suspend operator fun invoke(protocol: ProxyProtocol, proxies: List<Proxy>): List<Proxy> {
        return repository.checkProxies(protocol, proxies)
    }
}