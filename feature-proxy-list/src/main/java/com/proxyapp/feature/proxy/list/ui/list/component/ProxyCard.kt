package com.proxyapp.feature.proxy.list.ui.list.component

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.CountryTag
import com.proxyapp.core.ui.component.ProtocolTag
import com.proxyapp.core.ui.component.SpeedTag
import com.proxyapp.domain.model.Proxy
import com.proxyapp.feature.proxy.list.R

@Composable
fun ProxyCard(
    proxy: Proxy,
    onClick: () -> Unit,
    onMenuClick: () -> Unit,
) {
    val colorIndicator = if (proxy.isValid) { MaterialTheme.colorScheme.tertiary } else { MaterialTheme.colorScheme.error }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                ProxyIpWithIndicator(
                    ip = proxy.ip,
                    port = proxy.port,
                    color = colorIndicator
                )
                proxy.lastChecked?.let {
                    ProxyDateChecked(date = it)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                    ProtocolTag(proxy.protocol.name)
                    CountryTag(proxy.country.orEmpty())
                    SpeedTag(proxy.speed)
                }
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