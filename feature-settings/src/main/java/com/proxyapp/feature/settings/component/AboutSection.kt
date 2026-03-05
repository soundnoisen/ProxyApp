package com.proxyapp.feature.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.HorizontalDivider
import com.proxyapp.data.BuildConfig
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.feature.settings.R

@Composable
fun AboutSection(
    onSourceClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 16.dp)
        )
        Box(
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = MaterialTheme.colorScheme.surfaceTint)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VersionInfo()
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                ProtocolInfo()
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                SourceInfo { onSourceClick() }
            }
        }
    }
}

@Composable
private fun SourceInfo(
    onClick: () -> Unit
) {
    InfoContainer(
        icon = painterResource(R.drawable.ic_source),
        text = stringResource(R.string.label_source),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        content = {
            Icon(
                painter = painterResource(R.drawable.ic_navigate),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                contentDescription = null
            )
        },
        enabled = true,
        onClick = onClick
    )
}

@Composable
private fun ProtocolInfo() {
    InfoContainer(
        icon = painterResource(R.drawable.ic_protocols),
        text = stringResource(R.string.label_protocols),
        color = MaterialTheme.colorScheme.tertiary,
        content = {
            Text(
                text = ProxyProtocol.entries.joinToString("/") { it.toString() },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        onClick = {}
    )
}

@Composable
private fun VersionInfo() {
    InfoContainer(
        icon = painterResource(R.drawable.ic_version),
        text = stringResource(R.string.label_version),
        color = MaterialTheme.colorScheme.primary,
        content = {
            Text(
                text = BuildConfig.BUILD_TYPE.uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        onClick = {},
    )
}

@Composable
private fun InfoContainer(
    icon: Painter,
    text: String,
    color: Color,
    content: @Composable RowScope.() -> Unit,
    enabled: Boolean = false,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = { onClick() })
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Topic(icon = icon, color = color, text = text)
        content()
    }
}

@Composable
private fun Topic(
    icon: Painter,
    color: Color,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color.copy(0.16f), CircleShape)
                .size(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                tint = color,
                modifier = Modifier.size(26.dp),
                contentDescription = null
            )
        }
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}