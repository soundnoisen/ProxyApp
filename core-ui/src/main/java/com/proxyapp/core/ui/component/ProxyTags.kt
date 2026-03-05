package com.proxyapp.core.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.R
import com.proxyapp.core.ui.theme.TagColors
import com.proxyapp.core.ui.theme.TagColorsScheme
import com.proxyapp.domain.model.ThemeMode

@Composable
fun ProxyTags(
    currentTheme: ThemeMode,
    protocol: String,
    country: String,
    speed: Float,
    isManual: Boolean = false
) {
    val isDark = when(currentTheme) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val theme = if (isDark) TagColors.dark else TagColors.light

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProtocolTag(protocol, theme)
        if (country.isNotEmpty()) CountryTag(country, theme)
        if (speed != 0f) SpeedTag(speed, theme)
        if (isManual) ManualTag(theme)
    }
}


@Composable
private fun ProtocolTag(
    protocol: String,
    theme: TagColorsScheme,
) {
    TagContainer(color = theme.protocolBackground) {
        Text(
            text = protocol,
            style = MaterialTheme.typography.labelSmall,
            color = theme.protocolText
        )
    }
}

@Composable
private fun CountryTag(
    country: String,
    theme: TagColorsScheme
) {
    TagContainer(color = theme.countryBackground) {
        Icon(
            painter = painterResource(R.drawable.ic_country),
            contentDescription = country,
            tint = theme.countryText,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = country,
            style = MaterialTheme.typography.labelSmall,
            color = theme.countryText
        )
    }
}

@Composable
private fun SpeedTag(
    speed: Float,
    theme: TagColorsScheme,
) {
    val colors = when {
        speed <= 10 -> theme.speedHighBackground to theme.speedHighText
        speed <= 30 -> theme.speedMediumBackground to theme.speedMediumText
        else -> theme.speedLowBackground to theme.speedLowText
    }

    val (bgColor, textColor) = colors

    TagContainer(color = bgColor) {
        Icon(
            painter = painterResource(R.drawable.ic_speed),
            contentDescription = speed.toString(),
            tint = textColor,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "${speed}s",
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}

@Composable
private fun ManualTag(
    theme: TagColorsScheme
) {
    TagContainer(color = theme.countryBackground) {
        Text(
            text = stringResource(R.string.label_tag_manual),
            style = MaterialTheme.typography.labelSmall,
            color = theme.countryText
        )
    }
}