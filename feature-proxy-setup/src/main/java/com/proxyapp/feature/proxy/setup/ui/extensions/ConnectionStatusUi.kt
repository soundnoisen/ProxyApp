package com.proxyapp.feature.proxy.setup.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.proxyapp.feature.proxy.setup.R
import com.proxyapp.feature.proxy.setup.domain.ConnectionStatus

@Composable
fun ConnectionStatus.displayTitle(): String = when (this) {
    ConnectionStatus.DISCONNECTED -> stringResource(R.string.label_status_disconnected)
    ConnectionStatus.CONNECTING -> stringResource(R.string.label_status_connecting)
    ConnectionStatus.CONNECTED -> stringResource(R.string.label_status_connected)
}

@Composable
fun ConnectionStatus.displayHint(): String = when (this) {
    ConnectionStatus.DISCONNECTED -> stringResource(R.string.label_disconnect_hint)
    ConnectionStatus.CONNECTING -> stringResource(R.string.label_connecting_hint)
    ConnectionStatus.CONNECTED -> stringResource(R.string.label_connected_hint)
}
