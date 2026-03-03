package com.proxyapp.feature.proxy.list.domain

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.repository.SavedProxyRepository
import javax.inject.Inject

class ProxyExistsUseCase @Inject constructor(
    private val repository: SavedProxyRepository
) {
    suspend operator fun invoke(proxy: Proxy): Boolean {
        return repository.exists(proxy)
    }
}