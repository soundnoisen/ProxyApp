package com.proxyapp.domain.model

sealed class ProxyConnectionStatus {
    object Disconnected: ProxyConnectionStatus()
    object Connecting: ProxyConnectionStatus()
    object Disconnecting: ProxyConnectionStatus()
    object Connected: ProxyConnectionStatus()
    data class Error(val error: ProxyConnectionError): ProxyConnectionStatus()
}

enum class ProxyConnectionError {
    PROXY_NOT_SELECTED,
    TIMEOUT,
    HOST_NOT_FOUND,
    CONNECTION_REFUSED,
    UNKNOWN
}