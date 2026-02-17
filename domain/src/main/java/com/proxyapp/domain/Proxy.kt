package com.proxyapp.domain

data class Proxy(
    val id: Long,
    val ip: String,
    val port: Int,
    val speed: Float,
    val protocol: ProxyProtocol,
    val anonymity: ProxyAnonymity,
    val isValid: Boolean = false,
    val country: String? = null,
    val connectString: String? = null,
    val secret: String? = null,
    val username: String? = null,
    val password: String? = null,
)