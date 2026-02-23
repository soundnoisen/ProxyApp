package com.proxyapp.feature.proxy.list.domain

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.repository.ProxyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveProxiesUseCase @Inject constructor(
    private val repository: ProxyRepository
) {
    operator fun invoke(): Flow<List<Proxy>> {
        return repository.observeProxies()
    }
}
