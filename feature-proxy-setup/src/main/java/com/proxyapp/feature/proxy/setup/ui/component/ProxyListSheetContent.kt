package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.Title
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.domain.model.ThemeMode
import com.proxyapp.feature.proxy.setup.R

@Composable
fun ProxyListSheetContent(
    proxies: List<Proxy>,
    currentProxy: Proxy?,
    onConnectToTelegram: (Proxy) -> Unit,
    onSelectProxy: (Proxy) -> Unit,
    onMenuOpen: (Proxy) -> Unit,
    currentTheme: ThemeMode
) {
    val sortedProxies = remember(proxies, currentProxy) {
        proxies.sortedByDescending { it.id == currentProxy?.id }
    }

    LazyColumn {
        item {
            Title(title = stringResource(R.string.label_saved_proxies))
            Spacer(Modifier.height(16.dp))
        }
        if (sortedProxies.isNotEmpty()) {
            items(sortedProxies, key = { it.id }) { proxy ->
                val isCurrentProxy = proxy.id == currentProxy?.id
                val onClick = if (proxy.protocol == ProxyProtocol.MTPROTO) onConnectToTelegram else onSelectProxy
                ProxyCard(
                    proxy = proxy,
                    isCurrentProxy = isCurrentProxy,
                    onClick = { onClick(proxy) },
                    onMenuClick = { onMenuOpen(proxy) },
                    currentTheme = currentTheme,
                )
            }
        } else {
            item { PlaceholderSavedProxies() }
        }
    }
}