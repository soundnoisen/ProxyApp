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
import com.proxyapp.feature.proxy.list.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProxyMenuBottomSheet(
    onDismiss: () -> Unit,
    onCopy: () -> Unit,
    onSave: () -> Unit,
    onConnect: () -> Unit,
    telegramCompatible: Boolean,
    onConnectToTelegram: () -> Unit,
) {
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
            ProxyMenuButton(stringResource(R.string.label_menu_save)) { onSave() }
            HorizontalDivider()
            ProxyMenuButton(stringResource(R.string.label_menu_connect))  { onConnect() }
            if (telegramCompatible) {
                HorizontalDivider()
                ProxyMenuButton(stringResource(R.string.label_menu_connect_to_telegram)) { onConnectToTelegram() }
            }
        }
    }
}