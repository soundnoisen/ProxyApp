package com.proxyapp.core.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.proxyapp.core.ui.theme.TagColors

@Composable
fun ProtocolTag(
    protocol: String
) {
    val colors = if (isSystemInDarkTheme()) TagColors.dark else TagColors.light
    TagContainer(color = colors.protocolBackground) {
        Text(
            text = protocol,
            style = MaterialTheme.typography.labelMedium,
            color = colors.protocolText
        )
    }
}