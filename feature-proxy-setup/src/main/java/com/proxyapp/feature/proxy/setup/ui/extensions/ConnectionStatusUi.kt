package com.proxyapp.feature.proxy.setup.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.proxyapp.domain.model.ProxyConnectionStatus
import com.proxyapp.feature.proxy.setup.R

@Composable
fun ProxyConnectionStatus.displayTitle(): String = when (this) {
    ProxyConnectionStatus.Disconnected, is ProxyConnectionStatus.Error -> stringResource(R.string.label_status_disconnected)
    ProxyConnectionStatus.Connecting -> stringResource(R.string.label_status_connecting)
    ProxyConnectionStatus.Connected -> stringResource(R.string.label_status_connected)
    ProxyConnectionStatus.Disconnecting -> stringResource(R.string.label_status_disconnecting)
}

@Composable
fun ProxyConnectionStatus.displayHint(): String = when (this) {
    ProxyConnectionStatus.Disconnected, is ProxyConnectionStatus.Error -> stringResource(R.string.label_disconnect_hint)
    ProxyConnectionStatus.Connecting -> stringResource(R.string.label_waiting_hint)
    ProxyConnectionStatus.Connected -> stringResource(R.string.label_connected_hint)
    ProxyConnectionStatus.Disconnecting -> stringResource(R.string.label_waiting_hint)
}