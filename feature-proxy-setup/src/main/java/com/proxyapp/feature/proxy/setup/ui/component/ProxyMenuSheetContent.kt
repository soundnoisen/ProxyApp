package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.HorizontalDivider
import com.proxyapp.core.ui.component.ProxyMenuButton
import com.proxyapp.feature.proxy.setup.R

@Composable
fun ProxyMenuSheetContent(
    isCurrentProxy: Boolean,
    isConnected: Boolean,
    isTelegramCompatible: Boolean,
    onDismiss: () -> Unit,
    onCopy: () -> Unit,
    onDelete: () -> Unit,
    onConnectToTelegram: () -> Unit,
    onDisconnect: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        ProxyMenuButton(stringResource(R.string.label_menu_copy_to_clipboard)) { onCopy() }
        HorizontalDivider()
        ProxyMenuButton(stringResource(R.string.label_menu_delete)) { onDelete() }
        if (isCurrentProxy && isConnected) {
            ProxyMenuButton(stringResource(R.string.label_menu_disconnect)) { onDisconnect() }
            HorizontalDivider()
        }
        if (isTelegramCompatible) {
            ProxyMenuButton(stringResource(R.string.label_menu_connect_to_telegram)) { onConnectToTelegram() }
            HorizontalDivider()
        }
        ProxyMenuButton(stringResource(R.string.label_menu_close))  { onDismiss() }
    }
}