package com.proxyapp.feature.proxy.setup.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proxyapp.core.common.TelegramProxyMapper.toTelegramUrl
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyConnectionError
import com.proxyapp.domain.model.ProxyConnectionStatus
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.domain.model.ProxySource
import com.proxyapp.domain.model.SaveError
import com.proxyapp.domain.model.SaveResult
import com.proxyapp.domain.usecase.DeleteProxyUseCase
import com.proxyapp.domain.usecase.SaveProxyUseCase
import com.proxyapp.domain.usecase.SetCurrentProxyUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.CheckProxiesUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.ConnectProxyUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.DeleteCurrentProxyUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.DisconnectProxyUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.ObserveConnectionStatusUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.ObserveCurrentProxyUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.ObserveSavedProxiesUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.UpdateSavedProxiesUseCase
import com.proxyapp.feature.proxy.setup.domain.usecase.ValidateProxyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.UUID

@HiltViewModel
class ProxySetupViewModel @Inject constructor(
    observeCurrentProxy: ObserveCurrentProxyUseCase,
    observeConnectionStatus: ObserveConnectionStatusUseCase,
    observeSavedProxies: ObserveSavedProxiesUseCase,
    private val checkProxies: CheckProxiesUseCase,
    private val updateSavedProxies: UpdateSavedProxiesUseCase,
    private val validateProxy: ValidateProxyUseCase,
    private val saveProxy: SaveProxyUseCase,
    private val deleteProxy: DeleteProxyUseCase,
    private val connectProxy: ConnectProxyUseCase,
    private val disconnectProxy: DisconnectProxyUseCase,
    private val setCurrent: SetCurrentProxyUseCase,
    private val deleteCurrentProxy: DeleteCurrentProxyUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ProxySetupState())
    val state: StateFlow<ProxySetupState> = _state

    private val _effect = MutableSharedFlow<ProxySetupEffect>()
    val effect: SharedFlow<ProxySetupEffect> = _effect

    private var periodicCheckJob: Job? = null
    private val intervalMs = 5*60_000L

    init {
        observeCurrentProxy()
            .onEach { proxy ->
                _state.update { it.copy(currentProxy = proxy) }
            }
            .launchIn(viewModelScope)

        observeConnectionStatus()
            .distinctUntilChanged()
            .onEach { status ->
                _state.update { it.copy(connectionStatus = status) }
                if (status is ProxyConnectionStatus.Error) emitError(status.error)
            }
            .launchIn(viewModelScope)

        observeSavedProxies()
            .onEach { proxies ->
                _state.update { it.copy(savedProxy = proxies) }
            }
            .launchIn(viewModelScope)

        startPeriodicCheck()
    }


    private fun startPeriodicCheck() {
        periodicCheckJob?.cancel()
        periodicCheckJob = viewModelScope.launch {
            while (isActive) {
                val proxies = _state.value.savedProxy
                if (proxies.isEmpty()) {
                    delay(intervalMs)
                    continue
                }
                val checkable = proxies.filter { it.protocol != ProxyProtocol.MTPROTO }
                checkable.groupBy { it.protocol }.map { (protocol, proxies) ->
                    async { updateSavedProxies(checkProxies(protocol, proxies)) }
                }.awaitAll()
                delay(intervalMs)
            }
        }
    }

    fun onIntent(intent: ProxySetupIntent) {
        when (intent) {
            ProxySetupIntent.AddProxy -> handleAddProxy()
            is ProxySetupIntent.ChangeIp -> handleChangeIp(intent.ip)
            is ProxySetupIntent.ChangePort -> handleChangePort(intent.port)
            is ProxySetupIntent.ChangeProtocol -> handleChangeProtocol(intent.proxyProtocol)
            is ProxySetupIntent.ChangeUsername -> handleChangeUsername(intent.username)
            is ProxySetupIntent.ChangePassword -> handleChangePassword(intent.password)
            is ProxySetupIntent.ChangeSecret -> handleChangeSecret(intent.secret)
            ProxySetupIntent.Connect -> handleConnect()
            ProxySetupIntent.Disconnect -> handleDisconnect()
            ProxySetupIntent.NavigateToProxyList -> navigateToProxyList()
            ProxySetupIntent.HiddenSheet -> handleHiddenSheet()
            is ProxySetupIntent.ShowActionsSheet -> handleShowActionsSheet(intent.proxy)
            ProxySetupIntent.ShowAddSheet -> handleSheet(ProxySheetState.Add)
            ProxySetupIntent.ShowListSheet ->  handleSheet(ProxySheetState.List)
            is ProxySetupIntent.CopyToClipboard -> handleCopyProxy()
            is ProxySetupIntent.ConnectToTelegramProxy -> handleConnectToTelegramProxy(intent.proxy)
            ProxySetupIntent.DeleteProxy -> handleDeleteProxy()
            ProxySetupIntent.HiddenActionsSheet ->  handleSheet(ProxySheetState.List)
            is ProxySetupIntent.SelectProxy -> handleSelectProxy(intent.proxy)
            ProxySetupIntent.ResetAddForm -> handleResetAddForm()
        }
    }

    private fun handleResetAddForm() {
        clearData(ProxySheetState.Add)
    }

    private fun handleSelectProxy(proxy: Proxy) {
        _state.update { it.copy(currentProxy = proxy) }
        viewModelScope.launch { setCurrent(proxy) }
        handleHiddenSheet()
    }

    private fun handleDeleteProxy() {
        _state.value.selectedProxy?.let {
            viewModelScope.launch {
                if (_state.value.currentProxy?.id == it.id) deleteCurrentProxy()
                deleteProxy(it)
                handleSheet(ProxySheetState.List)
            }
        }
    }

    private fun handleConnectToTelegramProxy(proxy: Proxy?) {
        if (proxy == null) return
        _state.update { it.copy(selectedProxy = proxy) }
        proxy.toTelegramUrl()?.let {
            sendEffect(ProxySetupEffect.NavigateToTelegram(it))
        }
    }

    private fun handleCopyProxy() {
        _state.value.selectedProxy?.let {
            val text = it.connectString ?: "${it.ip}:${it.port}"
            handleSheet(ProxySheetState.List)
            sendEffect(ProxySetupEffect.CopyToClipboard(text))
        }
    }

    private fun handleChangeSecret(secret: String) {
        _state.update { it.copy(newProxySecret = secret, secretError = null) }
    }

    private fun handleShowActionsSheet(proxy: Proxy) {
        _state.update { it.copy(selectedProxy = proxy) }
        handleSheet(ProxySheetState.Actions(proxy))
    }

    private fun handleHiddenSheet() {
        if (_state.value.proxySheetState is ProxySheetState.Add) clearError()
        handleSheet(ProxySheetState.Hidden)
    }

    private fun handleSheet(proxySheetState: ProxySheetState) {
        _state.update { it.copy(proxySheetState = proxySheetState) }
    }

    private fun handleChangePort(port: String) {
        _state.update { it.copy(newProxyPort = port, portError = null) }
    }

    private fun handleChangeIp(ip: String) {
        _state.update { it.copy(newProxyIp = ip, ipError = null) }
    }

    private fun handleChangeUsername(username: String) {
        _state.update { it.copy(newProxyUsername = username, usernameError = null, passwordError = if (username.isBlank() && it.newProxyPassword.isNullOrBlank()) null else it.passwordError) }
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
        _state.value.currentProxy ?: return emitError(ProxyConnectionError.PROXY_NOT_SELECTED)
        connectProxy()
        handleHiddenSheet()
    }

    private fun handleDisconnect() {
        _state.value.currentProxy ?: return
        disconnectProxy()
        handleHiddenSheet()
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
            )
        }

        if (!isValid) return
        val state = _state.value

        val proxy = Proxy(
            id = UUID.randomUUID().toString(),
            ip = state.newProxyIp,
            port = state.newProxyPort.toInt(),
            protocol = state.newProxyProtocol,
            username = state.newProxyUsername,
            password = state.newProxyPassword,
            secret = state.newProxySecret,
            source = ProxySource.MANUAL
        )

        viewModelScope.launch {
            val result = saveProxy(proxy)
            when(result) {
                is SaveResult.Error -> {
                    handleHiddenSheet()
                    emitError(result.error)
                }
                SaveResult.Success -> {
                    clearData(ProxySheetState.Hidden)
                    sendEffect(ProxySetupEffect.ProxyAdded)
                    updateSavedProxies(checkProxies(proxy.protocol, listOf(proxy)))
                }
            }
        }
    }

    private fun clearData(proxySheetState: ProxySheetState) {
        val currentProxy = _state.value.currentProxy
        val currentConnectionStatus = _state.value.connectionStatus
        _state.value = ProxySetupState(currentProxy = currentProxy, connectionStatus = currentConnectionStatus, proxySheetState = proxySheetState)
    }

    private fun clearError() {
        _state.update {
            it.copy(
                ipError = null,
                portError = null,
                usernameError = null,
                passwordError = null,
                secretError = null,
            )
        }
    }

    private fun navigateToProxyList() {
        sendEffect(ProxySetupEffect.NavigateToProxies)
    }

    private fun emitError(error: SaveError) {
        sendEffect(ProxySetupEffect.ShowSaveError(error))
    }

    private fun emitError(error: ProxyConnectionError) {
        sendEffect(ProxySetupEffect.ShowError(error))
    }

    private fun sendEffect(effect: ProxySetupEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    override fun onCleared() {
        super.onCleared()
        periodicCheckJob?.cancel()
    }
}