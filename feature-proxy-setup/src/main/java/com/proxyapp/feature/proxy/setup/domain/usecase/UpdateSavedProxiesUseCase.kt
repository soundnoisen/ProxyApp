package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.repository.SavedProxyRepository
import javax.inject.Inject

class UpdateSavedProxiesUseCase @Inject constructor(
    private val repository: SavedProxyRepository
) {
    suspend operator fun invoke(proxies: List<Proxy>) {
        repository.update(proxies)
    }
}