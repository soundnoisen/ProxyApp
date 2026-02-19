package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.domain.ProxyProtocol
import com.proxyapp.feature.proxy.setup.domain.repository.ProxySetupRepository
import javax.inject.Inject

class AddProxyUseCase @Inject constructor(
    private val repository: ProxySetupRepository
) {
    operator fun invoke(
        ip: String,
        port: String,
        protocol: ProxyProtocol,
        username: String?,
        password: String?,
        secret: String?
    ) {
        repository.addProxy(ip, port, protocol, username, password, secret)
    }
}