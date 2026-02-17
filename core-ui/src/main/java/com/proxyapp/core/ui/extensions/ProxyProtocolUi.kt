package com.proxyapp.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.proxyapp.core.ui.R
import com.proxyapp.domain.ProxyProtocol

@Composable
fun ProxyProtocol.displayName(): String = when (this) {
    ProxyProtocol.HTTP -> stringResource(R.string.protocol_http)
    ProxyProtocol.MTPROTO -> stringResource(R.string.protocol_mtproto)
    ProxyProtocol.SOCKS5 -> stringResource(R.string.protocol_socks5)
}