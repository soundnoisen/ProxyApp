package com.proxyapp.core.common

object TelegramUtils {
    fun buildTelegramUrl(connectString: String?): String? {
        if (connectString.isNullOrBlank()) return null

        return when {
            connectString.startsWith("tg://") -> connectString
            connectString.startsWith("socks5://") -> {
                val cleaned = connectString.removePrefix("socks5://")
                val parts = cleaned.split(":")
                if (parts.size == 2) {
                    val server = parts[0]
                    val port = parts[1]
                    "tg://proxy?server=$server&port=$port"
                } else null
            }

            else -> null
        }
    }
}