package com.proxyapp.feature.proxy.list.ui.list.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proxyapp.domain.Proxy
import com.proxyapp.domain.ProxyProtocol

@Composable
fun ProxyListWithBottomActions(
    proxies: List<Proxy>,
    selectedProxy: Proxy?,
    isSheetVisible: Boolean,
    isLoading: Boolean,
    onMenuOpen: (Proxy) -> Unit,
    onDismiss: () -> Unit,
    onCardClick: (Proxy) -> Unit,
    onCopy: () -> Unit,
    onConnect: () -> Unit,
    onConnectToTelegram: () -> Unit,
    onSave: () -> Unit
) {
    if (proxies.isEmpty() && !isLoading) {
        EmptyProxyPlaceholder()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = proxies,
                key = { it.id }
            ) { proxy ->
                ProxyCard(
                    proxy = proxy,
                    onClick = { onCardClick(proxy) },
                    onMenuClick = { onMenuOpen(proxy) }
                )
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }

    if (isSheetVisible) {
        selectedProxy?.let { proxy ->
            ProxyMenuBottomSheet(
                onDismiss = onDismiss,
                onCopy = onCopy,
                onSave = onSave,
                onConnect = onConnect,
                telegramCompatible = proxy.protocol == ProxyProtocol.SOCKS5 || proxy.protocol == ProxyProtocol.MTPROTO,
                onConnectToTelegram = onConnectToTelegram
            )
        }
    }
}