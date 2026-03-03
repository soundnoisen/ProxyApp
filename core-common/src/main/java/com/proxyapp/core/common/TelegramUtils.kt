package com.proxyapp.core.common

import android.net.Uri
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyProtocol

object TelegramProxyMapper {

    fun Proxy.toTelegramUrl(): String? {
        return when (protocol) {
            ProxyProtocol.SOCKS5 -> { buildTelegramSocksUrl() }
            ProxyProtocol.MTPROTO -> { buildTelegramMTProtoUrl() }
            else -> null
        }
    }

    private fun Proxy.buildTelegramSocksUrl(): String {
        val builder = Uri.Builder()
            .scheme("https")
            .authority("t.me")
            .path("socks")
            .appendQueryParameter("server", ip)
            .appendQueryParameter("port", port.toString())

        username?.let { builder.appendQueryParameter("user", it) }
        password?.let { builder.appendQueryParameter("pass", it) }

        return builder.build().toString()
    }

    private fun Proxy.buildTelegramMTProtoUrl(): String {
        return Uri.Builder()
            .scheme("https")
            .authority("t.me")
            .path("proxy")
            .appendQueryParameter("server", ip)
            .appendQueryParameter("port", port.toString())
            .appendQueryParameter("secret", secret)
            .build()
            .toString()
    }
}