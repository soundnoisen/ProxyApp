package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.domain.ProxyProtocol
import com.proxyapp.feature.proxy.setup.domain.model.FieldError
import com.proxyapp.feature.proxy.setup.domain.model.ProxyValidationResult
import jakarta.inject.Inject

class ValidateProxyUseCase @Inject constructor() {

    operator fun invoke(
        ip: String,
        port: String,
        protocol: ProxyProtocol,
        username: String?,
        password: String?,
        secret: String?
    ): ProxyValidationResult {

        val trimmedIp = ip.trim()
        val trimmedPort = port.trim()
        val trimmedSecret = secret?.trim()

        val ipError = when {
            trimmedIp.isBlank() -> FieldError.BLANK
            !isValidHost(trimmedIp) -> FieldError.INVALID
            else -> null
        }

        val portError = when {
            trimmedPort.isBlank() -> FieldError.BLANK
            !isValidPort(trimmedPort) -> FieldError.INVALID
            else -> null
        }

        val secretError = if (protocol == ProxyProtocol.MTPROTO) {
            when {
                trimmedSecret.isNullOrBlank() -> FieldError.BLANK
                !isValidSecret(trimmedSecret) -> FieldError.INVALID
                else -> null
            }
        } else null

        val (usernameError, passwordError) =
            if (protocol != ProxyProtocol.MTPROTO) {
                validateAuth(username, password)
            } else null to null

        return ProxyValidationResult(
            ipError = ipError,
            portError = portError,
            usernameError = usernameError,
            passwordError = passwordError,
            secretError = secretError
        )
    }

    private fun validateAuth(username: String?, password: String?): Pair<FieldError?, FieldError?> {
        val u = username?.trim().orEmpty()
        val p = password?.trim().orEmpty()

        val isUsernameBlank = u.isBlank()
        val isPasswordBlank = p.isBlank()

        if (isUsernameBlank && isPasswordBlank) return null to null

        var usernameError: FieldError? = null
        var passwordError: FieldError? = null

        if (!isUsernameBlank && isPasswordBlank) usernameError = FieldError.BLANK
        if (!isPasswordBlank && isUsernameBlank) usernameError = FieldError.BLANK
        if (!isUsernameBlank && u.length < 3) usernameError = FieldError.INVALID
        if (!isPasswordBlank && p.length < 3) passwordError = FieldError.INVALID

        return usernameError to passwordError
    }

    private fun isValidPort(port: String): Boolean {
        val number = port.toIntOrNull() ?: return false
        return number in 1..65535
    }

    private fun isValidHost(host: String): Boolean {
        val ipRegex = Regex("""^(\d{1,3}\.){3}\d{1,3}$""")
        val domainRegex = Regex("""^[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$""")
        return ipRegex.matches(host) || domainRegex.matches(host)
    }

    private fun isValidSecret(secret: String?): Boolean {
        return secret?.matches(Regex("^[0-9a-fA-F]+$")) ?: false
    }
}