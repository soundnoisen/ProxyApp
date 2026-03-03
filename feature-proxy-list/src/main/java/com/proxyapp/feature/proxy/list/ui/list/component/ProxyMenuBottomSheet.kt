package com.proxyapp.feature.proxy.list.ui.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.HorizontalDivider
import com.proxyapp.core.ui.component.ProxyMenuButton
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.feature.proxy.list.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProxyMenuBottomSheet(
    onDismiss: () -> Unit,
    protocol: ProxyProtocol,
    isSelectedProxySaved: Boolean,
    onCopy: () -> Unit,
    onSave: () -> Unit,
    onRemove: () -> Unit,
    onConnect: () -> Unit,
    telegramCompatible: Boolean,
    onConnectToTelegram: () -> Unit,
) {
    val (actionStringRes, action) = if (isSelectedProxySaved) (R.string.label_menu_remove to onRemove) else (R.string.label_menu_save to onSave)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 8.dp)
        ) {
            ProxyMenuButton(stringResource(R.string.label_menu_copy_to_clipboard)) { onCopy() }
            HorizontalDivider()
            ProxyMenuButton(stringResource(actionStringRes)) { action() }
            if (protocol != ProxyProtocol.MTPROTO) {
                HorizontalDivider()
                ProxyMenuButton(stringResource(R.string.label_menu_connect)) { onConnect() }
            }
            if (telegramCompatible) {
                HorizontalDivider()
                ProxyMenuButton(stringResource(R.string.label_menu_connect_to_telegram)) { onConnectToTelegram() }
            }
        }
    }
}