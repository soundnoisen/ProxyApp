package com.proxyapp.feature.proxy.setup.ui

import com.proxyapp.feature.proxy.setup.domain.ProxyConnectionError

sealed class ProxySetupEffect {
    data class ShowError(val error: ProxyConnectionError): ProxySetupEffect()
    object ProxyAdded: ProxySetupEffect()
    object NavigateToProxies: ProxySetupEffect()
}