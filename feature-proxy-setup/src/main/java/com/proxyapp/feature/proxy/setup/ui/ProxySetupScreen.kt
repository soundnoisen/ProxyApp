package com.proxyapp.feature.proxy.setup.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.proxyapp.core.ui.component.SnackBar
import com.proxyapp.core.ui.extensions.displayName
import com.proxyapp.feature.proxy.setup.R
import com.proxyapp.feature.proxy.setup.domain.model.ConnectionStatus
import com.proxyapp.feature.proxy.setup.ui.component.ConnectButton
import com.proxyapp.feature.proxy.setup.ui.component.ConnectStatus
import com.proxyapp.feature.proxy.setup.ui.component.CurrentProxyCard
import com.proxyapp.feature.proxy.setup.ui.component.PlaceholderCard
import com.proxyapp.feature.proxy.setup.ui.component.ProxyAddBottomSheet
import com.proxyapp.feature.proxy.setup.ui.component.ProxySetupTopBar
import com.proxyapp.feature.proxy.setup.ui.extensions.displayHint
import com.proxyapp.feature.proxy.setup.ui.extensions.displayName
import com.proxyapp.feature.proxy.setup.ui.extensions.displayTitle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProxySetupScreen(
    viewModel: ProxySetupViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var isError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ProxySetupEffect.ShowError -> {
                    isError = true
                    snackBarHostState.showSnackbar(effect.error.displayName(context))
                }
                ProxySetupEffect.ProxyAdded -> {
                    isError = false
                    snackBarHostState.showSnackbar(context.resources.getString(R.string.message_proxy_added))
                }
                ProxySetupEffect.NavigateToProxies -> Unit
            }
        }
    }
    Scaffold(
        topBar = {
            ProxySetupTopBar { viewModel.onIntent(ProxySetupIntent.ToggleSheet) }
        },
        snackbarHost = {
            SnackBar(
                snackBarHostState = snackBarHostState,
                isError = isError
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(Modifier.height(80.dp))
            ConnectButton(
                status = state.connectionStatus,
                enabled = !(state.connectionStatus == ConnectionStatus.CONNECTING || state.connectionStatus == ConnectionStatus.DISCONNECTING),
                onClick = {
                    viewModel.onIntent(
                        if (state.connectionStatus == ConnectionStatus.CONNECTED) ProxySetupIntent.Disconnect
                        else ProxySetupIntent.Connect
                    )
                }
            )

            Spacer(Modifier.height(16.dp))

            ConnectStatus(
                status = state.connectionStatus.displayTitle(),
                hint = state.connectionStatus.displayHint()
            )
            Spacer(Modifier.height(120.dp))

            state.currentProxy?.let { proxy ->
                CurrentProxyCard(
                    ip = proxy.ip,
                    port = proxy.port.toString(),
                    protocol = proxy.protocol.displayName(),
                    country = proxy.country.orEmpty(),
                    speed = proxy.speed,
                    onClick = { viewModel.onIntent(ProxySetupIntent.NavigateToProxyList) }
                )
            } ?: PlaceholderCard { viewModel.onIntent(ProxySetupIntent.NavigateToProxyList) }

            if (state.isSheetVisible) {
                ProxyAddBottomSheet(
                    ip = state.newProxyIp,
                    port = state.newProxyPort,
                    protocol = state.newProxyProtocol,
                    username = state.newProxyUsername,
                    password = state.newProxyPassword,
                    secret = state.newProxySecret,
                    onIpChange = { viewModel.onIntent(ProxySetupIntent.ChangeIp(it)) },
                    onPortChange = { viewModel.onIntent(ProxySetupIntent.ChangePort(it)) },
                    onProtocolChange = { viewModel.onIntent(ProxySetupIntent.ChangeProtocol(it)) },
                    onUsernameChange = { viewModel.onIntent(ProxySetupIntent.ChangeUsername(it)) },
                    onPasswordChange = { viewModel.onIntent(ProxySetupIntent.ChangePassword(it)) },
                    onSecretChange = { viewModel.onIntent(ProxySetupIntent.ChangeSecret(it)) },
                    ipError = state.ipError?.displayName(),
                    portError = state.portError?.displayName(),
                    secretError = state.secretError?.displayName(),
                    usernameError = state.usernameError?.displayName(),
                    passwordError = state.passwordError?.displayName(),
                    onConfirm = { viewModel.onIntent(ProxySetupIntent.AddProxy) },
                    onDismiss = { viewModel.onIntent(ProxySetupIntent.ToggleSheet) })
            }
        }
    }
}