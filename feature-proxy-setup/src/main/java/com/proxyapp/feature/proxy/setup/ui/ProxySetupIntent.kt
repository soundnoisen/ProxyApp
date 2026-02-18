package com.proxyapp.feature.proxy.setup.ui

import com.proxyapp.domain.ProxyProtocol

sealed class ProxySetupIntent {
    data class ChangeIp(val ip: String): ProxySetupIntent()
    data class ChangePort(val port: String): ProxySetupIntent()
    data class ChangeProtocol(val proxyProtocol: ProxyProtocol): ProxySetupIntent()
    data class ChangeUsername(val username: String): ProxySetupIntent()
    data class ChangePassword(val password: String): ProxySetupIntent()
    data class ChangeSecret(val secret: String): ProxySetupIntent()
    object Connect: ProxySetupIntent()
    object Disconnect: ProxySetupIntent()
    object ToggleSheet: ProxySetupIntent()
    object AddProxy: ProxySetupIntent()
    object NavigateToProxyList: ProxySetupIntent()
}