package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.CountryTag
import com.proxyapp.core.ui.component.ProtocolTag
import com.proxyapp.core.ui.component.SpeedTag

@Composable
fun CurrentProxyCard(
    ip: String,
    port: String,
    protocol: String,
    country: String,
    speed: Float,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick
    ) {
        Text(
            text = "$ip:$port",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProtocolTag(protocol = protocol)
            CountryTag(country = country)
            SpeedTag(speed = speed)
        }
    }
}