package com.proxyapp.domain.model

data class Proxy(
    val id: Long,
    val ip: String,
    val port: Int,
    val speed: Float,
    val protocol: ProxyProtocol,
    val isValid: Boolean = false,
    val country: String? = null,
    val connectString: String? = null,
    val lastChecked: String? = null,
    val secret: String? = null,
    val username: String? = null,
    val password: String? = null,
)

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