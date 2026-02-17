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
fun CountryTag(
    country: String
) {
    val colors = if (isSystemInDarkTheme()) TagColors.dark else TagColors.light
    TagContainer(color = colors.countryBackground) {
        Icon(
            painter = painterResource(R.drawable.ic_country),
            contentDescription = country,
            tint = colors.countryText,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = country,
            style = MaterialTheme.typography.labelMedium,
            color = colors.countryText
        )
    }
}