package com.proxyapp.domain.usecase

import com.proxyapp.domain.repository.ProxyConnectionManager
import javax.inject.Inject

class RestoreConnectionUseCase @Inject constructor(
    private val connectionManager: ProxyConnectionManager
) {
    suspend operator fun invoke() {
        connectionManager.restoreConnection()
    }
}