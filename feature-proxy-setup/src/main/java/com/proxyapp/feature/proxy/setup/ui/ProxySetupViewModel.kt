package com.proxyapp.feature.proxy.setup.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ProxySetupViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(ProxySetupState())
    val state: StateFlow<ProxySetupState> = _state

    private val _effect = MutableSharedFlow<ProxySetupEffect>()
    val effect: SharedFlow<ProxySetupEffect> = _effect

    fun onIntent(intent: ProxySetupIntent) {
        _state.value = reduce(_state.value, intent)

        when (intent) {
            is ProxySetupIntent.AddProxy -> addProxy()
            is ProxySetupIntent.Connect -> connect()
            is ProxySetupIntent.Disconnect -> disconnect()
            is ProxySetupIntent.NavigateToProxyList -> navigateToProxyList()
            else -> Unit
        }
    }

    private fun addProxy() {}

    private fun connect() {}

    private fun disconnect() {}

    private fun navigateToProxyList() {}

}