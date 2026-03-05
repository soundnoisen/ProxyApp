package com.proxyapp.feature.settings

sealed class SettingsEffect {
    object NavigateToSource: SettingsEffect()
}