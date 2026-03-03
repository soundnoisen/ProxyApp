package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.proxyapp.core.ui.component.EmptyPlaceholder
import com.proxyapp.feature.proxy.setup.R

@Composable
fun PlaceholderSavedProxies() {
    EmptyPlaceholder(
        icon = painterResource(R.drawable.ic_warning),
        title = stringResource(R.string.placeholder_saved_proxies),
        description = stringResource(R.string.placeholder_hint_saved_proxies)
    )
}