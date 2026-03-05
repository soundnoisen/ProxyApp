package com.proxyapp.feature.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.domain.model.ThemeMode
import com.proxyapp.feature.settings.R
import com.proxyapp.feature.settings.extensions.displayIcon
import com.proxyapp.feature.settings.extensions.displayTitle

@Composable
fun ThemeSelection(
    currentTheme: ThemeMode,
    onSetTheme: (ThemeMode) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.theme),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ThemeMode.entries.forEach { theme ->
                ThemeCard(
                    modifier = Modifier.weight(1f),
                    icon = theme.displayIcon(),
                    label = theme.displayTitle(),
                    isActive = currentTheme == theme,
                    onSetTheme = { onSetTheme(theme) }
                )
            }
        }
    }
}

@Composable
private fun ThemeCard(
    modifier: Modifier,
    icon: Painter,
    label: String,
    isActive: Boolean,
    onSetTheme: () -> Unit
) {
    val (contentColor, backgroundColor) = if (isActive) (MaterialTheme.colorScheme.onPrimary to MaterialTheme.colorScheme.primary)
    else (MaterialTheme.colorScheme.onSurfaceVariant to MaterialTheme.colorScheme.surface)

    Box(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = MaterialTheme.colorScheme.surfaceTint)
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .clickable{ onSetTheme() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = icon,
                tint = contentColor,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor
            )
        }
    }
}