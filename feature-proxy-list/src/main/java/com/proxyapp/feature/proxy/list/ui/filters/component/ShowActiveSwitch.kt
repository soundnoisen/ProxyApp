package com.proxyapp.feature.proxy.list.ui.filters.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.proxyapp.feature.proxy.list.R

@Composable
fun ShowActiveSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        label = stringResource(R.string.label_show_active),
        hint = stringResource(R.string.hint_show_active),
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}