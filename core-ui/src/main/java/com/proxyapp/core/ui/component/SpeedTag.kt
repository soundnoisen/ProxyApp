package com.proxyapp.core.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.R
import com.proxyapp.core.ui.theme.TagColors

@Composable
fun SpeedTag(
    speed: Float
) {
    val theme = if (isSystemInDarkTheme()) TagColors.dark else TagColors.light

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
            style = MaterialTheme.typography.labelMedium,
            color = textColor
        )
    }
}