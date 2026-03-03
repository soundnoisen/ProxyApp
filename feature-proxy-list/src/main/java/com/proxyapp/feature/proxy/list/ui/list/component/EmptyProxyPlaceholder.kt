package com.proxyapp.feature.proxy.list.ui.list.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.proxyapp.core.ui.component.EmptyPlaceholder
import com.proxyapp.feature.proxy.list.R

@Composable
fun EmptyProxyPlaceholder() {
    EmptyPlaceholder(
        icon = painterResource(R.drawable.ic_empty),
        title = stringResource(R.string.placeholder_proxy_list),
        description = stringResource(R.string.placeholder_hint_proxy_list)
    )
}