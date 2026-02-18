package com.proxyapp.feature.proxy.setup.domain

sealed class ProxyConnectionResult {
    object Success: ProxyConnectionResult()
    data class Error(val error: ProxyConnectionError): ProxyConnectionResult()
}

enum class ProxyConnectionError {
    TIMEOUT,
    HOST_NOT_FOUND,
    CONNECTION_REFUSED,
    UNKNOWN
}