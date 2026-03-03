package com.proxyapp.feature.proxy.setup.ui

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyProtocol

sealed class ProxySetupIntent {
    data class ChangeIp(val ip: String): ProxySetupIntent()
    data class ChangePort(val port: String): ProxySetupIntent()
    data class ChangeProtocol(val proxyProtocol: ProxyProtocol): ProxySetupIntent()
    data class ChangeUsername(val username: String): ProxySetupIntent()
    data class ChangePassword(val password: String): ProxySetupIntent()
    data class ChangeSecret(val secret: String): ProxySetupIntent()
    object Connect: ProxySetupIntent()
    data class SelectProxy(val proxy: Proxy): ProxySetupIntent()
    object Disconnect: ProxySetupIntent()
    object ShowListSheet: ProxySetupIntent()
    object ShowAddSheet: ProxySetupIntent()
    data class ShowActionsSheet(val proxy: Proxy): ProxySetupIntent()
    object HiddenActionsSheet: ProxySetupIntent()
    object HiddenSheet: ProxySetupIntent()
    object ResetAddForm: ProxySetupIntent()
    object AddProxy: ProxySetupIntent()
    object DeleteProxy: ProxySetupIntent()
    object CopyToClipboard: ProxySetupIntent()
    object NavigateToProxyList: ProxySetupIntent()
    data class ConnectToTelegramProxy(val proxy: Proxy?): ProxySetupIntent()
}