package com.proxyapp.feature.proxy.list.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proxyapp.core.common.TelegramProxyMapper.toTelegramUrl
import com.proxyapp.domain.model.LoadError
import com.proxyapp.domain.model.LoadProgress
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyFilters
import com.proxyapp.domain.model.SaveResult
import com.proxyapp.domain.usecase.DeleteProxyUseCase
import com.proxyapp.domain.usecase.SaveProxyUseCase
import com.proxyapp.domain.usecase.SetCurrentProxyUseCase
import com.proxyapp.feature.proxy.list.domain.LoadNextPageUseCase
import com.proxyapp.feature.proxy.list.domain.ObserveFiltersUseCase
import com.proxyapp.feature.proxy.list.domain.ObserveProxiesUseCase
import com.proxyapp.feature.proxy.list.domain.ProxyExistsUseCase
import com.proxyapp.feature.proxy.list.domain.RefreshProxiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProxyListViewModel @Inject constructor(
    observeFilters: ObserveFiltersUseCase,
    observeProxies: ObserveProxiesUseCase,
    private val refreshProxies: RefreshProxiesUseCase,
    private val loadNextPage: LoadNextPageUseCase,
    private val proxyExists: ProxyExistsUseCase,
    private val saveProxy: SaveProxyUseCase,
    private val removeProxy: DeleteProxyUseCase,
    private val setCurrent: SetCurrentProxyUseCase
): ViewModel() {

    private val _proxies = MutableStateFlow<List<Proxy>>(emptyList())
    val proxies: StateFlow<List<Proxy>> = _proxies

    private val _currentFilters = MutableStateFlow(ProxyFilters())

    private val _state = MutableStateFlow(ProxyListState())
    val state: StateFlow<ProxyListState> = _state

    private val _effect = MutableSharedFlow<ProxyListEffect>()
    val effect: SharedFlow<ProxyListEffect> = _effect

    init {
        observeFilters()
            .onEach { filters ->
                _currentFilters.value = filters
                refresh()
            }
            .launchIn(viewModelScope)

        observeProxies()
            .onEach { list ->
                _proxies.value = list
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: ProxyListIntent) {
        when(intent) {
            ProxyListIntent.ConnectProxy -> handleConnectProxy()
            ProxyListIntent.ConnectToTelegramProxy -> handleConnectToTelegramProxy()
            ProxyListIntent.CopyProxy -> handleCopyProxy()
            ProxyListIntent.NavigateToFilters -> handleNavigateToFilters()
            ProxyListIntent.Refresh -> refresh()
            ProxyListIntent.SaveProxy -> handleSaveProxy()
            ProxyListIntent.CloseMenu -> handleCloseMenu()
            is ProxyListIntent.OpenMenu -> handleOpenMenu(intent.proxy)
            is ProxyListIntent.CardClicked -> handleCardClicked(intent.proxy)
            ProxyListIntent.Load -> load()
            ProxyListIntent.RemoveProxy -> handleRemoveProxy()
        }
    }

    private fun handleRemoveProxy() {
        _state.value.proxySelected?.let { proxy ->
            viewModelScope.launch {
                removeProxy(proxy)
                _state.update { it.copy(isSelectedProxySaved = !it.isSelectedProxySaved) }
                sendEffect(ProxyListEffect.ProxyRemove)
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            handleLoadProgress(refreshProxies(_currentFilters.value))
        }
    }

    private fun load() {
        viewModelScope.launch {
            handleLoadProgress(loadNextPage(_currentFilters.value))
        }
    }

    private fun handleLoadProgress(flow: Flow<LoadProgress>) {
        flow.onEach { result ->
            when (result) {
                LoadProgress.Loading -> _state.update { it.copy(isLoading = true) }
                LoadProgress.Success -> _state.update { it.copy(isLoading = false) }
                is LoadProgress.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    emitError(result.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun handleOpenMenu(proxy: Proxy) {
        proxySelect(proxy)
        toggleSheet()
    }

    private fun handleCardClicked(proxy: Proxy) {
        proxySelect(proxy)
        handleCopyProxy()
    }

    private fun proxySelect(proxy: Proxy) {
        viewModelScope.launch {
            _state.update { it.copy(proxySelected = proxy, isSelectedProxySaved = proxyExists(proxy)) }
        }
    }

    private fun handleCloseMenu() {
        _state.update { it.copy(proxySelected = null) }
        toggleSheet()
    }

    private fun toggleSheet() {
        _state.update { it.copy(isSheetVisible = !it.isSheetVisible) }
    }

    private fun handleSaveProxy() {
        val selected = _state.value.proxySelected ?: return
        viewModelScope.launch {
            val result = saveProxy(selected)
            if (result is SaveResult.Success) {
                _state.update { it.copy(isSelectedProxySaved = !it.isSelectedProxySaved) }
                sendEffect(ProxyListEffect.ProxySaved)
            }
        }
    }

    private fun handleNavigateToFilters() {
        sendEffect(ProxyListEffect.NavigateToFilters)
    }

    private fun handleCopyProxy() {
        _state.value.proxySelected?.let {
            val text = it.connectString ?: "${it.ip}:${it.port}"
            sendEffect(ProxyListEffect.CopyToClipboard(text))
        }
    }

    private fun handleConnectToTelegramProxy() {
        _state.value.proxySelected?.let { proxy ->
            proxy.toTelegramUrl()?.let {
                sendEffect(ProxyListEffect.NavigateToTelegram(it))
            }
        }
    }

    private fun handleConnectProxy() {
        val selected = _state.value.proxySelected ?: return
        viewModelScope.launch {
            setCurrent(selected)
            toggleSheet()
            sendEffect(ProxyListEffect.NavigateToProxySetup)
        }
    }

    private fun emitError(error: LoadError) {
        sendEffect(ProxyListEffect.ShowLoadError(error))
    }

    private fun sendEffect(effect: ProxyListEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}