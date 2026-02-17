package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.feature.proxy.setup.R

@Composable
fun ProxyInfoForm(
    ip: String,
    port: String,
    onIpChange: (String) -> Unit,
    onPortChange: (String) -> Unit,
    errorIp: String?,
    errorPort: String?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TextField(
            value = ip,
            onValueChange = onIpChange,
            placeholder = stringResource(R.string.placeholder_ip),
            errorText = errorIp,
            modifier = Modifier.weight(1f)
        )
        TextField(
            value = port,
            onValueChange = onPortChange,
            placeholder = stringResource(R.string.placeholder_port),
            errorText = errorPort,
            isNumber = true,
            modifier = Modifier.weight(1f)
        )
    }
}