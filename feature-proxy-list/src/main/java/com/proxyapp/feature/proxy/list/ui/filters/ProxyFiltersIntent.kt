package com.proxyapp.feature.proxy.list.ui.filters

import com.proxyapp.domain.model.ProxyProtocol

sealed class ProxyFiltersIntent {
    object NavigateToBack: ProxyFiltersIntent()
    object Reset: ProxyFiltersIntent()
    object ToggleActiveChecker: ProxyFiltersIntent()
    object ToggleCountriesBottomSheet: ProxyFiltersIntent()
    data class SelectProtocol(val protocol: ProxyProtocol): ProxyFiltersIntent()
    data class UnselectProtocol(val protocol: ProxyProtocol): ProxyFiltersIntent()
    data class ChangeSpeed(val start: Float, val end: Float): ProxyFiltersIntent()
    data class AddCountry(val iso: String): ProxyFiltersIntent()
    data class RemoveCountry(val iso: String): ProxyFiltersIntent()
    object ToggleTelegramCompatibleChecker: ProxyFiltersIntent()
}