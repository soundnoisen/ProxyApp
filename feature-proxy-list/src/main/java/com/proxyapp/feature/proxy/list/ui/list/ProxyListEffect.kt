package com.proxyapp.feature.proxy.list.ui.list

import com.proxyapp.domain.model.LoadError
import com.proxyapp.domain.model.SaveError

sealed class ProxyListEffect {
    object NavigateToFilters: ProxyListEffect()
    object NavigateToProxySetup: ProxyListEffect()
    data class ShowLoadError(val error: LoadError): ProxyListEffect()
    data class ShowSaveError(val error: SaveError): ProxyListEffect()
    data class NavigateToTelegram(val url: String): ProxyListEffect()
    data class CopyToClipboard(val text: String): ProxyListEffect()
    object ProxySaved: ProxyListEffect()
}