package com.proxyapp.feature.proxy.setup.domain.model

sealed class ProxyConnectionProgress {
    object Connecting: ProxyConnectionProgress()
    object Disconnecting: ProxyConnectionProgress()
    object Success: ProxyConnectionProgress()
    data class Error(val error: ProxyConnectionError): ProxyConnectionProgress()
}

enum class ProxyConnectionError {
    PROXY_NOT_SELECTED,
    TIMEOUT,
    HOST_NOT_FOUND,
    CONNECTION_REFUSED,
    UNKNOWN
}