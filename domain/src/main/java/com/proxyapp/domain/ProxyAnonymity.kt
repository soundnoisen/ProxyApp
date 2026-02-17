package com.proxyapp.domain

enum class ProxyAnonymity {
    ELITE, ANONYMOUS, TRANSPARENT;

    companion object {
        private val mapping = mapOf(
            "elite" to ELITE,
            "anonymous" to ANONYMOUS,
            "transparent" to TRANSPARENT
        )
        fun fromString(value: String) = mapping[value.lowercase()] ?: TRANSPARENT
    }
}