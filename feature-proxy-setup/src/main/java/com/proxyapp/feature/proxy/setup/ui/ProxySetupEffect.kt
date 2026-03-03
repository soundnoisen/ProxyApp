package com.proxyapp.feature.proxy.setup.ui

import com.proxyapp.domain.model.ProxyConnectionError
import com.proxyapp.domain.model.SaveError

sealed class ProxySetupEffect {
    data class ShowError(val error: ProxyConnectionError): ProxySetupEffect()
    data class ShowSaveError(val error: SaveError): ProxySetupEffect()
    object ProxyAdded: ProxySetupEffect()
    object NavigateToProxies: ProxySetupEffect()
    data class CopyToClipboard(val text: String): ProxySetupEffect()
    data class NavigateToTelegram(val url: String): ProxySetupEffect()
}