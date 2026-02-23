package com.proxyapp.feature.proxy.list.ui.filters

import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.domain.model.SpeedRange

data class ProxyFiltersState(
    val activeOnly: Boolean = false,
    val protocols: List<ProxyProtocol> = emptyList(),
    val speedRange: SpeedRange = SpeedRange(),
    val countriesIso: List<String> = emptyList(),
    val telegramFilter: Boolean = false,
    val isSheetVisible: Boolean = false,
    val isLoading: Boolean = false
)