package com.proxyapp.domain.usecase

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.repository.ProxyConnectionManager
import javax.inject.Inject

class SetCurrentProxyUseCase @Inject constructor(
    private val manager: ProxyConnectionManager
) {
    suspend operator fun invoke(proxy: Proxy) {
        manager.setCurrentProxy(proxy)
    }
}