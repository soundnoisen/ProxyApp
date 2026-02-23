package com.proxyapp.feature.proxy.list.ui.filters.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.proxyapp.feature.proxy.list.R

@Composable
fun TelegramCompatibleSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        label = stringResource(R.string.label_telegram_compatible),
        hint = stringResource(R.string.hint_telegram_compatible),
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}