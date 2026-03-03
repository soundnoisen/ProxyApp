package com.proxyapp.domain.extensions

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyProtocol

fun Proxy.toServerString(): String {
    return when (protocol) {
        ProxyProtocol.HTTP, ProxyProtocol.SOCKS5 -> {
            if (!username.isNullOrBlank() && !password.isNullOrBlank()) {
                "${username}:${password}@${ip}:${port}"
            } else {
                "${ip}:${port}"
            }
        }
        ProxyProtocol.MTPROTO -> {
            "${ip}:$.port}:${secret}"
        }
    }
}