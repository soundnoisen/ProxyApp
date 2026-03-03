package com.proxyapp.feature.proxy.setup.ui.extensions

import android.content.Context
import com.proxyapp.domain.model.SaveError
import com.proxyapp.feature.proxy.setup.R

fun SaveError.displayName(context: Context): String = when (this) {
    SaveError.UNKNOWN -> context.getString(R.string.error_save_unknown)
    SaveError.EXISTS -> context.getString(R.string.error_save_exists)
}