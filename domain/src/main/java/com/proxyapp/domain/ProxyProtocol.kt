package com.proxyapp.domain

enum class ProxyProtocol {
    HTTP, MTPROTO, SOCKS5;

    companion object {
        private val mapping = mapOf(
            "http" to HTTP,
            "mtproto" to MTPROTO,
            "socks5" to SOCKS5
        )
        fun fromString(value: String) = mapping[value.lowercase()] ?: HTTP
    }
}