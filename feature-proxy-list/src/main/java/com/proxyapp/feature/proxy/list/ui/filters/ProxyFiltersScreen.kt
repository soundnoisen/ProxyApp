package com.proxyapp.feature.proxy.list.ui.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.proxyapp.core.ui.component.CircularProgress
import com.proxyapp.feature.proxy.list.ui.filters.component.CountriesBottomSheet
import com.proxyapp.feature.proxy.list.ui.filters.component.CountryFilter
import com.proxyapp.feature.proxy.list.ui.filters.component.ProtocolFilter
import com.proxyapp.feature.proxy.list.ui.filters.component.ProxyFiltersTopBar
import com.proxyapp.feature.proxy.list.ui.filters.component.ShowActiveSwitch
import com.proxyapp.feature.proxy.list.ui.filters.component.SpeedFilter
import com.proxyapp.feature.proxy.list.ui.filters.component.TelegramCompatibleSwitch
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProxyFiltersScreen(
    onBack: () -> Unit,
    viewModel: ProxyFiltersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
             when(effect) {
                 ProxyFiltersEffect.NavigateToBack -> onBack()
             }
        }
    }

    Scaffold(
        topBar = {
            ProxyFiltersTopBar(
                onReset = { viewModel.onIntent(ProxyFiltersIntent.Reset) },
                onBackClick = { viewModel.onIntent(ProxyFiltersIntent.NavigateToBack) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.isLoading) {
                item { CircularProgress() }
            } else {
                item {
                    ShowActiveSwitch(
                        checked = state.activeOnly,
                        onCheckedChange = { viewModel.onIntent(ProxyFiltersIntent.ToggleActiveChecker) }
                    )
                }
                item {
                    ProtocolFilter(
                        protocolsSelected = state.protocols,
                        onClick = {
                            if (state.protocols.contains(it)) {
                                viewModel.onIntent(ProxyFiltersIntent.UnselectProtocol(it))
                            } else {
                                viewModel.onIntent(ProxyFiltersIntent.SelectProtocol(it))
                            }
                        }
                    )
                }
                item {
                    SpeedFilter(
                        value = (state.speedRange.minSpeed..state.speedRange.maxSpeed),
                        onValueChange = {
                            viewModel.onIntent(
                                ProxyFiltersIntent.ChangeSpeed(
                                    it.start,
                                    it.endInclusive
                                )
                            )
                        }
                    )
                }
                item {
                    CountryFilter(
                        selectedCountries = state.countriesIso,
                        openSheet = { viewModel.onIntent(ProxyFiltersIntent.ToggleCountriesBottomSheet) },
                        onDelete = { viewModel.onIntent(ProxyFiltersIntent.RemoveCountry(it)) }
                    )
                }
                item {
                    TelegramCompatibleSwitch(
                        checked = state.telegramFilter,
                        onCheckedChange = { viewModel.onIntent(ProxyFiltersIntent.ToggleTelegramCompatibleChecker) }
                    )
                }
            }
        }
        if (state.isSheetVisible) {
            CountriesBottomSheet(
                selectedCountries = state.countriesIso,
                onSelect = { if (!state.countriesIso.contains(it)) { viewModel.onIntent(ProxyFiltersIntent.AddCountry(it)) } },
                onDelete = { viewModel.onIntent(ProxyFiltersIntent.RemoveCountry(it)) },
                onDismiss = { viewModel.onIntent(ProxyFiltersIntent.ToggleCountriesBottomSheet) }
            )
        }
    }
}