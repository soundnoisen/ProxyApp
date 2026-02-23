package com.proxyapp.domain.model

data class ProxyFilters(
    val activeOnly: Boolean = false,
    val protocols: List<ProxyProtocol> = emptyList(),
    val speedRange: SpeedRange = SpeedRange(),
    val countriesIso: List<String> = emptyList(),
    val telegramFilter: Boolean = false
)

data class SpeedRange(
    val minSpeed: Float = 0f,
    val maxSpeed: Float = 60f
)