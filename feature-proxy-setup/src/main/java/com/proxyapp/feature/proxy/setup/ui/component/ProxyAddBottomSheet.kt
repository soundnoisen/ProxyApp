package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.Title
import com.proxyapp.domain.ProxyProtocol
import com.proxyapp.feature.proxy.setup.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProxyAddBottomSheet(
    ip: String,
    port: String,
    protocol: ProxyProtocol,
    username: String?,
    password: String?,
    secret: String?,
    onIpChange: (String) -> Unit,
    onPortChange: (String) -> Unit,
    onProtocolChange: (ProxyProtocol) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSecretChange: (String) -> Unit,
    ipError: String?,
    portError: String?,
    secretError: String?,
    passwordError: String?,
    usernameError: String?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Title(title = stringResource(R.string.title_add_proxy))
            ProxyInfoForm(
                ip = ip,
                onIpChange = onIpChange,
                ipError = ipError,
                port = port,
                onPortChange = onPortChange,
                portError = portError
            )
            ProxyProtocolSegmentedSliding(
                selectedProtocol = protocol,
                onProtocolChange = onProtocolChange,
                secret = secret,
                onSecretChange = onSecretChange,
                username = username,
                password = password,
                onUsernameChange = onUsernameChange,
                onPasswordChange = onPasswordChange,
                secretError = secretError,
                passwordError = passwordError,
                usernameError = usernameError,
            )
            ProxyAddActions(
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
        }
    }
}