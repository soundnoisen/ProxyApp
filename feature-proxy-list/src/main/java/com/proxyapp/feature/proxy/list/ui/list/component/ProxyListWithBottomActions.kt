package com.proxyapp.feature.proxy.list.ui.list.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyProtocol

@Composable
fun ProxyListWithBottomActions(
    proxies: List<Proxy>,
    selectedProxy: Proxy?,
    isSheetVisible: Boolean,
    isSelectedProxySaved: Boolean,
    isLoading: Boolean,
    onMenuOpen: (Proxy) -> Unit,
    onDismiss: () -> Unit,
    onCardClick: (Proxy) -> Unit,
    onCopy: () -> Unit,
    onConnect: () -> Unit,
    onConnectToTelegram: () -> Unit,
    onSave: () -> Unit,
    onRemove: () -> Unit,
    onLoadNextPage: () -> Unit
) {
    if (proxies.isEmpty() && !isLoading) {
        EmptyProxyPlaceholder()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(proxies) { proxy ->
                ProxyCard(
                    proxy = proxy,
                    onClick = { onCardClick(proxy) },
                    onMenuClick = { onMenuOpen(proxy) }
                )
            }
            item {
                LaunchedEffect(Unit) {
                    onLoadNextPage()
                }
            }
        }
    }
    if (isSheetVisible) {
        selectedProxy?.let { proxy ->
            ProxyMenuBottomSheet(
                isSelectedProxySaved = isSelectedProxySaved,
                protocol = proxy.protocol,
                onDismiss = onDismiss,
                onCopy = onCopy,
                onSave = onSave,
                onRemove = onRemove,
                onConnect = onConnect,
                telegramCompatible = proxy.protocol == ProxyProtocol.SOCKS5 || proxy.protocol == ProxyProtocol.MTPROTO,
                onConnectToTelegram = onConnectToTelegram
            )
        }
    }
}