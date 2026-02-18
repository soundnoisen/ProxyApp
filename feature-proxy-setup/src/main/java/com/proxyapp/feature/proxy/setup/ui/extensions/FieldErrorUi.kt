package com.proxyapp.feature.proxy.setup.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.proxyapp.feature.proxy.setup.R
import com.proxyapp.feature.proxy.setup.domain.FieldError

@Composable
fun FieldError.displayName(): String = when (this) {
    FieldError.BLANK -> stringResource(R.string.error_field_empty)
    FieldError.INVALID -> stringResource(R.string.error_invalid_format)
}