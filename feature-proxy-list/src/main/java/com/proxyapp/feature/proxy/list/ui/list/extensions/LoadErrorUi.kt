package com.proxyapp.feature.proxy.list.ui.list.extensions

import android.content.Context
import com.proxyapp.domain.model.LoadError
import com.proxyapp.feature.proxy.list.R

fun LoadError.displayName(context: Context): String = when (this) {
    LoadError.NETWORK -> context.getString(R.string.error_load_network)
    LoadError.UNKNOWN -> context.getString(R.string.error_load_unknown)
}