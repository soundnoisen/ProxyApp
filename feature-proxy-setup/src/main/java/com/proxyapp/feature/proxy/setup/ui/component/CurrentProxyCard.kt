package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.proxyapp.core.ui.component.ProxyTags
import com.proxyapp.domain.model.ThemeMode

@Composable
fun CurrentProxyCard(
    ip: String,
    port: String,
    protocol: String,
    country: String,
    speed: Float,
    onClick: () -> Unit,
    currentTheme: ThemeMode
) {
    Card(
        onClick = onClick
    ) {
        Text(
            text = "$ip:$port",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        ProxyTags(
            protocol = protocol,
            country = country,
            speed = speed,
            currentTheme = currentTheme
        )
    }
}