package com.proxyapp.feature.proxy.list.ui.list.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.proxyapp.feature.proxy.list.R

@Composable
fun ProxyDateChecked(date: String) {
    Text(
        text = stringResource(R.string.label_date_checked)+": $date",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}