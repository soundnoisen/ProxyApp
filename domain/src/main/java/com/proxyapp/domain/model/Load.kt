package com.proxyapp.domain.model

sealed class LoadProgress {
    object Loading: LoadProgress()
    object Success: LoadProgress()
    data class Error(val error: LoadError): LoadProgress()
}

enum class LoadError {
    NETWORK,
    UNKNOWN
}