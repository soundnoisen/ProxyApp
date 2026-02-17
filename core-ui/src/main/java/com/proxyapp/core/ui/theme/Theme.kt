package com.proxyapp.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkOnSurfaceVariant,
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    outline = DarkOutline,
    surfaceContainer = DarkSurfaceContainer,
    error = DarkError,
    outlineVariant = DarkOutlineVariant,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary
)

private val LightColorScheme = lightColorScheme(
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    onSurfaceVariant = LightOnSurfaceVariant,
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    outline = LightOutline,
    surfaceContainer = LightSurfaceContainer,
    error = LightError,
    outlineVariant = LightOutlineVariant,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary
)

@Composable
fun ProxyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}