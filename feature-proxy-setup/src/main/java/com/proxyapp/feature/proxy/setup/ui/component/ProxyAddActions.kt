package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.ConfirmButton
import com.proxyapp.core.ui.component.DisableButton
import com.proxyapp.feature.proxy.setup.R

@Composable
fun ProxyAddActions(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DisableButton(
            onClick = { onDismiss() },
            modifier = Modifier.weight(1f)
        )
        ConfirmButton(
            onClick = { onConfirm() },
            text = stringResource(R.string.action_add_proxy),
            modifier = Modifier.weight(1f)
        )
    }
}