package com.proxyapp.feature.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.proxyapp.domain.model.ThemeMode
import com.proxyapp.feature.settings.R

@Composable
fun ThemeMode.displayTitle(): String = when (this) {
    ThemeMode.LIGHT -> stringResource(R.string.label_light_theme)
    ThemeMode.DARK -> stringResource(R.string.label_dark_theme)
    ThemeMode.SYSTEM -> stringResource(R.string.label_system_theme)
}

@Composable
fun ThemeMode.displayIcon(): Painter = when (this) {
    ThemeMode.LIGHT -> painterResource(R.drawable.ic_light_theme)
    ThemeMode.DARK -> painterResource(R.drawable.ic_dark_theme)
    ThemeMode.SYSTEM -> painterResource(R.drawable.ic_system_theme)
}