package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.proxyapp.core.ui.component.TagContainer
import com.proxyapp.core.ui.theme.TagColors
import com.proxyapp.feature.proxy.setup.R

@Composable
fun ManualTag() {
    val theme = if (isSystemInDarkTheme()) TagColors.dark else TagColors.light

    TagContainer(color = theme.countryBackground) {
        Text(
            text = stringResource(R.string.label_tag_manual),
            style = MaterialTheme.typography.labelSmall,
            color = theme.countryText
        )
    }
}