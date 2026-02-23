package com.proxyapp.feature.proxy.list.ui.list.extensions

import android.content.Context
import com.proxyapp.domain.model.SaveError
import com.proxyapp.feature.proxy.list.R

fun SaveError.displayName(context: Context): String = when (this) {
    SaveError.UNKNOWN -> context.getString(R.string.error_save_unknown)
}