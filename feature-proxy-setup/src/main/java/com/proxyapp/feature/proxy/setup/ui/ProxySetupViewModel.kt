package com.proxyapp.feature.proxy.setup.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proxyapp.domain.ProxyProtocol
import com.proxyapp.feature.proxy.setup.domain.model.ConnectionStatus
import com.proxyapp.feature.proxy.setup.domain.model.ProxyConnectionError
import com.proxyapp.feature.proxy.setup.domain.model.ProxyConnectionProgress
import com.proxyapp.feature.proxy.setup.domain.usecase.AddProxyUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.ConnectToProxyUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.DisconnectProxyUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.ValidateProxyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProxySetupViewModel @Inject constructor(
    private val validateProxy: ValidateProxyUseCase,
    private val addProxy: AddProxyUseCase,
    private val connectToProxy: ConnectToProxyUseCase,
    private val disconnectProxy: DisconnectProxyUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ProxySetupState())
    val state: StateFlow<ProxySetupState> = _state

    private val _effect = MutableSharedFlow<ProxySetupEffect>()
    val effect: SharedFlow<ProxySetupEffect> = _effect

    private var connectJob: Job? = null

    fun onIntent(intent: ProxySetupIntent) {
        when (intent) {
            ProxySetupIntent.AddProxy -> handleAddProxy()
            is ProxySetupIntent.ChangeIp -> handleChangeIp(intent.ip)
            is ProxySetupIntent.ChangePort -> handleChangePort(intent.port)
            is ProxySetupIntent.ChangeProtocol -> handleChangeProtocol(intent.proxyProtocol)
            is ProxySetupIntent.ChangeUsername -> handleChangeUsername(intent.username)
            is ProxySetupIntent.ChangePassword -> handleChangePassword(intent.password)
            is ProxySetupIntent.ChangeSecret -> handleChangeSecret(intent.secret)
            ProxySetupIntent.ToggleSheet -> handleToggleSheet()
            ProxySetupIntent.Connect -> handleConnect()
            ProxySetupIntent.Disconnect -> handleDisconnect()
            ProxySetupIntent.NavigateToProxyList -> navigateToProxyList()
        }
    }

    private fun handleChangeSecret(secret: String) {
        _state.update { it.copy(newProxySecret = secret, secretError = null) }
    }

    private fun handleToggleSheet() {
        _state.update { it.copy(isSheetVisible = !it.isSheetVisible) }
        clearError()
    }

    private fun handleChangePort(port: String) {
        _state.update { it.copy(newProxyPort = port, portError = null) }
    }

    private fun handleChangeIp(ip: String) {
        _state.update { it.copy(newProxyIp = ip, ipError = null) }
    }

    private fun handleChangeUsername(username: String) {
        _state.update {
            it.copy(
                newProxyUsername = username,
                usernameError = null,
                passwordError = if (username.isBlank() && it.newProxyPassword.isNullOrBlank()) null else it.passwordError
            )
        }
    }

    private fun handleChangePassword(password: String) {
        _state.update {
            it.copy(
                newProxyPassword = password,
                passwordError = null,
                usernameError = if (password.isBlank() && it.newProxyUsername.isNullOrBlank()) null else it.usernameError
            )
        }
    }

    private fun handleChangeProtocol(protocol: ProxyProtocol) {
        _state.update {
            it.copy(
                newProxyProtocol = protocol,
                usernameError = null,
                passwordError = null,
                secretError = null
            )
        }
    }

    private fun handleConnect() {
        val proxy = _state.value.currentProxy
        if (proxy != null) {
            connectJob?.cancel()
            connectJob = viewModelScope.launch {
                _state.update { it.copy(connectionStatus = ConnectionStatus.CONNECTING) }
                connectToProxy(proxy).collect { progress ->
                    when (progress) {
                        ProxyConnectionProgress.Success -> {
                            _state.update { it.copy(connectionStatus = ConnectionStatus.CONNECTED) }
                        }
                        is ProxyConnectionProgress.Error -> {
                            _state.update { it.copy(connectionStatus = ConnectionStatus.DISCONNECTED) }
                            emitError(progress.error)
                        }
                        else -> Unit
                    }
                }
            }
        } else emitError(ProxyConnectionError.PROXY_NOT_SELECTED)
    }

    private fun handleDisconnect() {
        connectJob?.cancel()
        connectJob = viewModelScope.launch {
            _state.update { it.copy(connectionStatus = ConnectionStatus.DISCONNECTING) }
            disconnectProxy().collect { progress ->
                when (progress) {
                    ProxyConnectionProgress.Success -> {
                        _state.update { it.copy(connectionStatus = ConnectionStatus.DISCONNECTED) }
                    }
                    is ProxyConnectionProgress.Error -> {
                        _state.update { it.copy(connectionStatus = ConnectionStatus.CONNECTED) }
                        emitError(progress.error)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun handleAddProxy() {
        val result = validateProxy(
            ip = _state.value.newProxyIp,
            port = _state.value.newProxyPort,
            protocol = _state.value.newProxyProtocol,
            username = _state.value.newProxyUsername,
            password = _state.value.newProxyPassword,
            secret = _state.value.newProxySecret
        )

        val isValid = result.isValid(_state.value.newProxyProtocol)

        _state.update {
            it.copy(
                ipError = result.ipError,
                portError = result.portError,
                usernameError = result.usernameError,
                passwordError = result.passwordError,
                secretError = result.secretError,
                isSheetVisible = !isValid
            )
        }

        if (isValid) {
            val state = _state.value
            addProxy(
                ip = state.newProxyIp,
                port = state.newProxyPort,
                protocol = state.newProxyProtocol,
                username = state.newProxyUsername,
                password = state.newProxyPassword,
                secret = state.newProxySecret,
            )
            sendEffect(ProxySetupEffect.ProxyAdded)
            clearData()
        }
    }

    private fun clearData() {
        val currentProxy = _state.value.currentProxy
        val currentConnectionStatus = _state.value.connectionStatus
        _state.value = ProxySetupState(currentProxy = currentProxy, connectionStatus = currentConnectionStatus)
    }

    private fun clearError() {
        _state.update {
            it.copy(
                ipError = null,
                portError = null,
                usernameError = null,
                passwordError = null,
                secretError = null
            )
        }
    }

    private fun navigateToProxyList() {
        sendEffect(ProxySetupEffect.NavigateToProxies)
    }

    private fun emitError(error: ProxyConnectionError) {
        sendEffect(ProxySetupEffect.ShowError(error))
    }

    private fun sendEffect(effect: ProxySetupEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}