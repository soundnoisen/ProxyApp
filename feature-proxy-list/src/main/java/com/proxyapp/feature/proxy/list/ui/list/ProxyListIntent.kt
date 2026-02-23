package com.proxyapp.feature.proxy.list.ui.list

import com.proxyapp.domain.model.Proxy

sealed class ProxyListIntent() {
    object Load: ProxyListIntent()
    object Refresh: ProxyListIntent()
    object NavigateToFilters: ProxyListIntent()
    object CloseMenu: ProxyListIntent()
    data class CardClicked(val proxy: Proxy): ProxyListIntent()
    data class OpenMenu(val proxy: Proxy): ProxyListIntent()
    object CopyProxy: ProxyListIntent()
    object SaveProxy: ProxyListIntent()
    object ConnectProxy: ProxyListIntent()
    object ConnectToTelegramProxy: ProxyListIntent()
}