package com.proxyapp.feature.proxy.list.ui.list

import com.proxyapp.domain.model.Proxy

data class ProxyListState(
    val proxySelected: Proxy? = null,
    val isSheetVisible: Boolean = false,
    val isLoading: Boolean = false
)