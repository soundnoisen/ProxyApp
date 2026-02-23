package com.proxyapp.feature.proxy.list.ui.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proxyapp.domain.model.ProxyFilters
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.domain.model.SpeedRange
import com.proxyapp.feature.proxy.list.domain.ObserveFiltersUseCase
import com.proxyapp.feature.proxy.list.domain.SaveFiltersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProxyFiltersViewModel @Inject constructor(
    private val saveFiltersUseCase: SaveFiltersUseCase,
    observeFilters: ObserveFiltersUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ProxyFiltersState())
    val state: StateFlow<ProxyFiltersState> = _state

    private val _effect = MutableSharedFlow<ProxyFiltersEffect>()
    val effect: SharedFlow<ProxyFiltersEffect> = _effect

    init {
        _state.update { it.copy(isLoading = true) }
        observeFilters()
            .onEach { filters ->
                _state.update { filters.toState().copy(isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: ProxyFiltersIntent) {
        when(intent) {
            is ProxyFiltersIntent.AddCountry -> handleAddCountry(intent.iso)
            is ProxyFiltersIntent.ChangeSpeed -> handleChangeSpeed(intent.start, intent.end)
            ProxyFiltersIntent.NavigateToBack -> handleNavigateToBack()
            is ProxyFiltersIntent.RemoveCountry -> handleRemoveCountry(intent.iso)
            ProxyFiltersIntent.Reset -> handleReset()
            is ProxyFiltersIntent.SelectProtocol -> handleSelectProtocol(intent.protocol)
            ProxyFiltersIntent.ToggleActiveChecker -> handleToggleActiveChecker()
            ProxyFiltersIntent.ToggleTelegramCompatibleChecker -> handleToggleTelegramCompatibleChecker()
            is ProxyFiltersIntent.UnselectProtocol -> handleUnselectProtocol(intent.protocol)
            ProxyFiltersIntent.ToggleCountriesBottomSheet -> handleToggleCountriesBottomSheet()
        }
    }

    private fun handleNavigateToBack() {
        viewModelScope.launch {
            saveFiltersUseCase.invoke(_state.value.toDomain())
            sendEffect(ProxyFiltersEffect.NavigateToBack)
        }
    }

    private fun handleToggleCountriesBottomSheet() {
        _state.update { it.copy(isSheetVisible = !it.isSheetVisible) }
    }

    private fun handleChangeSpeed(start: Float, end: Float) {
        _state.update { it.copy(speedRange = SpeedRange(start, end)) }
    }

    private fun handleToggleTelegramCompatibleChecker() {
        _state.update { it.copy(telegramFilter = !it.telegramFilter) }
    }

    private fun handleToggleActiveChecker() {
        _state.update { it.copy(activeOnly = !it.activeOnly) }
    }

    private fun handleSelectProtocol(protocol: ProxyProtocol) {
        _state.update { it.copy(protocols = it.protocols + protocol) }
    }

    private fun handleUnselectProtocol(protocol: ProxyProtocol) {
        _state.update { it.copy(protocols = it.protocols - protocol) }
    }

    private fun handleAddCountry(iso: String) {
        _state.update { it.copy(countriesIso = it.countriesIso + iso) }
    }

    private fun handleRemoveCountry(iso: String) {
        _state.update { it.copy(countriesIso = it.countriesIso - iso) }
    }

    private fun handleReset() {
        _state.value = ProxyFiltersState()
    }

    private fun sendEffect(effect: ProxyFiltersEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}

private fun ProxyFilters.toState() = ProxyFiltersState(
    activeOnly = activeOnly,
    telegramFilter = telegramFilter,
    speedRange = speedRange,
    protocols = protocols,
    countriesIso = countriesIso
)

private fun ProxyFiltersState.toDomain() = ProxyFilters(
    activeOnly = activeOnly,
    telegramFilter = telegramFilter,
    speedRange = speedRange,
    protocols = protocols,
    countriesIso = countriesIso
)