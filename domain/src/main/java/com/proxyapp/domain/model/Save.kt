package com.proxyapp.domain.model

sealed class SaveResult {
    object Success: SaveResult()
    data class Error(val error: SaveError): SaveResult()
}

enum class SaveError {
    UNKNOWN,
    EXISTS
}