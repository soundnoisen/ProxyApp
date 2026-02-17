package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.proxyapp.feature.proxy.setup.R


@Composable
fun PlaceholderCard(
    onClick: () -> Unit
) {
    Card(
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.label_placeholder_proxy),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}