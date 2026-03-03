package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.domain.repository.ProxyConnectionManager
import javax.inject.Inject

class DeleteCurrentProxyUseCase @Inject constructor(
    private val manager: ProxyConnectionManager
) {
    suspend operator fun invoke() {
        manager.removeCurrentProxy()
    }
}
