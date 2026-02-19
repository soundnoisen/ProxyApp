package com.proxyapp.feature.proxy.setup.ui.extensions

import android.content.Context
import com.proxyapp.feature.proxy.setup.R
import com.proxyapp.feature.proxy.setup.domain.model.ProxyConnectionError

fun ProxyConnectionError.displayName(context: Context): String = when(this) {
    ProxyConnectionError.PROXY_NOT_SELECTED -> context.getString(R.string.error_proxy_not_selected)
    ProxyConnectionError.TIMEOUT -> context.getString(R.string.error_proxy_timeout)
    ProxyConnectionError.HOST_NOT_FOUND -> context.getString(R.string.error_proxy_host_not_found)
    ProxyConnectionError.CONNECTION_REFUSED -> context.getString(R.string.error_proxy_connection_refused)
    ProxyConnectionError.UNKNOWN -> context.getString(R.string.error_proxy_unknown)
}