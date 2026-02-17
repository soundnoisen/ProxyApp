package com.proxyapp.feature.proxy.setup.ui.extensions

import com.proxyapp.feature.proxy.setup.R
import com.proxyapp.feature.proxy.setup.domain.ConnectionStatus

fun ConnectionStatus.titleRes(): Int = when (this) {
    ConnectionStatus.DISCONNECTED -> R.string.label_status_disconnected
    ConnectionStatus.CONNECTING -> R.string.label_status_connecting
    ConnectionStatus.CONNECTED -> R.string.label_status_connected
}

fun ConnectionStatus.hintRes(): Int = when (this) {
    ConnectionStatus.DISCONNECTED -> R.string.label_disconnect_hint
    ConnectionStatus.CONNECTING -> R.string.label_connecting_hint
    ConnectionStatus.CONNECTED -> R.string.label_connected_hint
}
