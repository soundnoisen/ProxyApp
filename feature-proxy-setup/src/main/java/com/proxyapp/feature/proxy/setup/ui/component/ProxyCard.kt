package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.ProxyIpWithIndicator
import com.proxyapp.core.ui.component.ProxyTags
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxySource
import com.proxyapp.domain.model.ThemeMode
import com.proxyapp.feature.proxy.setup.R

@Composable
fun ProxyCard(
    proxy: Proxy,
    isCurrentProxy: Boolean,
    onClick: () -> Unit,
    onMenuClick: () -> Unit,
    currentTheme: ThemeMode,
) {
    val colorIndicator =
        when(proxy.source) {
            ProxySource.API -> if (proxy.isValid && proxy.speed > 0f) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
            ProxySource.MANUAL -> if (proxy.speed > 0f)  MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
        }
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .padding(start = 16.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isCurrentProxy)
                    Text(
                        text = stringResource(R.string.label_current_proxy),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ProxyIpWithIndicator(
                    ip = proxy.ip,
                    port = proxy.port,
                    color = colorIndicator
                )
                ProxyTags(
                    protocol = proxy.protocol.name.uppercase(),
                    country = proxy.country.orEmpty(),
                    speed = proxy.speed,
                    isManual = proxy.source == ProxySource.MANUAL,
                    currentTheme = currentTheme
                )
            }
            IconButton(onClick = onMenuClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_menu),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}