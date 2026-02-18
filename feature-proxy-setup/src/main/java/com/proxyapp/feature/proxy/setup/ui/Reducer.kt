package com.proxyapp.feature.proxy.setup.ui

import android.util.Patterns
import com.proxyapp.domain.ProxyProtocol
import com.proxyapp.feature.proxy.setup.domain.ConnectionStatus
import com.proxyapp.feature.proxy.setup.domain.FieldError

fun reduce(state: ProxySetupState, intent: ProxySetupIntent): ProxySetupState {
    return when(intent) {
        ProxySetupIntent.AddProxy -> handleAddProxy(state)
        is ProxySetupIntent.ChangeIp -> state.copy(newProxyIp = intent.ip, ipError = null)
        is ProxySetupIntent.ChangePort -> state.copy(newProxyPort = intent.port, portError = null)
        is ProxySetupIntent.ChangeProtocol ->  state.copy(newProxyProtocol = intent.proxyProtocol)
        is ProxySetupIntent.ChangeUsername -> handleChangeUsername(state, intent.username)
        is ProxySetupIntent.ChangePassword -> handleChangePassword(state, intent.password)
        is ProxySetupIntent.ChangeSecret -> state.copy(newProxySecret = intent.secret, secretError = null)
        ProxySetupIntent.ToggleSheet -> state.copy(isSheetVisible = !state.isSheetVisible)
        ProxySetupIntent.Connect -> handleConnect(state)
        ProxySetupIntent.Disconnect -> state.copy(connectionStatus = ConnectionStatus.DISCONNECTED)
        ProxySetupIntent.NavigateToProxyList -> state
    }
}

private fun handleAddProxy(state: ProxySetupState): ProxySetupState {
    return validateProxy(state)
}

private fun handleConnect(state: ProxySetupState): ProxySetupState {
    return if (state.currentProxy != null) {
        state.copy(connectionStatus = ConnectionStatus.CONNECTING)
    } else {
        state
    }
}

private fun handleChangeUsername(state: ProxySetupState, username: String): ProxySetupState {
    return state.copy(
        newProxyUsername = username,
        usernameError = null,
        passwordError = if (username.isBlank() && state.newProxyPassword.isNullOrBlank()) null else state.passwordError
    )
}

private fun handleChangePassword(state: ProxySetupState, password: String): ProxySetupState {
    return state.copy(
        newProxyPassword = password,
        passwordError = null,
        usernameError = if (password.isBlank() && state.newProxyUsername.isNullOrBlank()) null else state.usernameError
    )
}

private fun validateProxy(state: ProxySetupState): ProxySetupState {
    val ip = state.newProxyIp.trim()
    val port = state.newProxyPort.trim()
    val protocol = state.newProxyProtocol
    val secret = state.newProxySecret?.trim()

    val ipErrorRes = when {
        ip.isBlank() -> FieldError.BLANK
        !isValidHost(ip) -> FieldError.INVALID
        else -> null
    }

    val portErrorRes = when {
        port.isBlank() -> FieldError.BLANK
        !isValidPort(port) -> FieldError.INVALID
        else -> null
    }

    val secretErrorRes = if (protocol == ProxyProtocol.MTPROTO) {
        when {
            secret.isNullOrBlank() -> FieldError.BLANK
            !isValidSecret(secret) -> FieldError.INVALID
            else -> null
        }
    } else {
        null
    }

    val tempState = state.copy(ipError = ipErrorRes, portError = portErrorRes, secretError = secretErrorRes)

    val newState = if (protocol != ProxyProtocol.MTPROTO) {
        validateAuthFields(tempState)
    } else {
        tempState
    }

    return validateResult(newState, protocol)
}

private fun validateResult(state: ProxySetupState, protocol: ProxyProtocol): ProxySetupState {
    var result = state
    if (state.ipError == null && state.portError == null) {
        if (protocol == ProxyProtocol.MTPROTO && state.secretError == null) {
            result = state.copy(isSheetVisible = false)
        }
        if (protocol != ProxyProtocol.MTPROTO && state.usernameError == null && state.passwordError == null) {
            result = state.copy(isSheetVisible = false)
        }
    }
    return result
}

private fun isValidPort(port: String): Boolean {
    val number = port.toIntOrNull() ?: return false
    return number in 1..65535
}

private fun isValidHost(host: String): Boolean {
    return Patterns.DOMAIN_NAME.matcher(host).matches() ||
            Patterns.IP_ADDRESS.matcher(host).matches()
}

private fun isValidSecret(secret: String?): Boolean {
    return secret?.matches(Regex("^[0-9a-fA-F]+$")) ?: false
}

private fun validateAuthFields(state: ProxySetupState): ProxySetupState {
    val username = state.newProxyUsername?.trim().orEmpty()
    val password = state.newProxyPassword?.trim().orEmpty()

    val isUsernameBlank = username.isBlank()
    val isPasswordBlank = password.isBlank()

    var usernameError: FieldError? = null
    var passwordError: FieldError? = null

    if (isUsernameBlank && isPasswordBlank) {
        return state
    }

    if (!isUsernameBlank && isPasswordBlank) {
        passwordError = FieldError.BLANK
    }

    if (!isPasswordBlank && isUsernameBlank) {
        usernameError = FieldError.BLANK
    }

    if (!isUsernameBlank && username.length < 3) {
        usernameError = FieldError.INVALID
    }

    if (!isPasswordBlank && password.length < 3) {
        passwordError = FieldError.INVALID
    }

    return state.copy(
        usernameError = usernameError,
        passwordError = passwordError
    )
}