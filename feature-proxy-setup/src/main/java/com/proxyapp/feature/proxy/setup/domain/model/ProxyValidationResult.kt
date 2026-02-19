package com.proxyapp.feature.proxy.setup.domain.model

import com.proxyapp.domain.ProxyProtocol

data class ProxyValidationResult(
    val ipError: FieldError? = null,
    val portError: FieldError? = null,
    val usernameError: FieldError? = null,
    val passwordError: FieldError? = null,
    val secretError: FieldError? = null,
) {
    fun isValid(protocol: ProxyProtocol): Boolean {
        if (ipError != null || portError != null) return false
        return when (protocol) {
            ProxyProtocol.MTPROTO -> secretError == null
            else -> usernameError == null && passwordError == null
        }
    }
}