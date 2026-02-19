package com.proxyapp.feature.proxy.setup.ui

import com.proxyapp.domain.Proxy
import com.proxyapp.domain.ProxyProtocol
import com.proxyapp.feature.proxy.setup.domain.model.ConnectionStatus
import com.proxyapp.feature.proxy.setup.domain.model.FieldError

data class ProxySetupState(
    val currentProxy: Proxy? = null,
    val connectionStatus: ConnectionStatus = ConnectionStatus.DISCONNECTED,
    val isSheetVisible: Boolean = false,
    val newProxyIp: String = "",
    val newProxyPort: String = "",
    val newProxyProtocol: ProxyProtocol = ProxyProtocol.HTTP,
    val newProxyUsername: String? = null,
    val newProxyPassword: String? = null,
    val newProxySecret: String? = null,
    val errorConnect: String? = null,
    val ipError: FieldError? = null,
    val portError: FieldError? = null,
    val usernameError: FieldError? = null,
    val passwordError: FieldError? = null,
    val secretError: FieldError? = null,
)