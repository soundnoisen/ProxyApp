package com.proxyapp.feature.proxy.setup.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.proxyapp.core.common.openUrl
import com.proxyapp.core.ui.component.SnackBar
import com.proxyapp.core.ui.extensions.displayName
import com.proxyapp.domain.model.ProxyConnectionStatus
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.feature.proxy.setup.R
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.AddProxy
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.ChangeIp
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.ChangePassword
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.ChangePort
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.ChangeProtocol
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.ChangeSecret
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.ChangeUsername
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.Connect
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.ConnectToTelegramProxy
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.CopyToClipboard
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.DeleteProxy
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.Disconnect
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.HiddenSheet
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.ResetAddForm
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.SelectProxy
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.ShowActionsSheet
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.ShowAddSheet
import com.proxyapp.feature.proxy.setup.ui.ProxySetupIntent.ShowListSheet
import com.proxyapp.feature.proxy.setup.ui.component.ConnectButton
import com.proxyapp.feature.proxy.setup.ui.component.ConnectStatus
import com.proxyapp.feature.proxy.setup.ui.component.CurrentProxyCard
import com.proxyapp.feature.proxy.setup.ui.component.PlaceholderCard
import com.proxyapp.feature.proxy.setup.ui.component.ProxyAppSheetContent
import com.proxyapp.feature.proxy.setup.ui.component.ProxyListSheetContent
import com.proxyapp.feature.proxy.setup.ui.component.ProxyMenuSheetContent
import com.proxyapp.feature.proxy.setup.ui.component.ProxySetupTopBar
import com.proxyapp.feature.proxy.setup.ui.extensions.displayHint
import com.proxyapp.feature.proxy.setup.ui.extensions.displayName
import com.proxyapp.feature.proxy.setup.ui.extensions.displayTitle
import com.proxyapp.feature.proxy.setup.ui.permission.VpnPermissionRequester
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProxySetupScreen(
    viewModel: ProxySetupViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var isError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val messageProxyAdded = stringResource(R.string.message_proxy_added)
    val messageProxyCopied = stringResource(R.string.message_proxy_copied)

    val requestVpnPermission = VpnPermissionRequester(onPermissionGranted = { viewModel.onIntent(Connect) })

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ProxySetupEffect.ShowError -> {
                    isError = true
                    snackBarHostState.showSnackbar(effect.error.displayName(context))
                }
                is ProxySetupEffect.ShowSaveError -> {
                    isError = true
                    snackBarHostState.showSnackbar(effect.error.displayName(context))
                }
                ProxySetupEffect.ProxyAdded -> {
                    isError = false
                    snackBarHostState.showSnackbar(messageProxyAdded)
                }
                is ProxySetupEffect.CopyToClipboard -> {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("proxy", effect.text))
                    Toast.makeText(context, messageProxyCopied, Toast.LENGTH_SHORT).show()
                }
                is ProxySetupEffect.NavigateToTelegram -> { context.openUrl(effect.url) }
                ProxySetupEffect.NavigateToProxies -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            ProxySetupTopBar { viewModel.onIntent(ShowAddSheet) }
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
                enabled = !(state.connectionStatus == ProxyConnectionStatus.Connecting || state.connectionStatus == ProxyConnectionStatus.Disconnecting),
                onClick = {
                    if (state.connectionStatus == ProxyConnectionStatus.Connected) {
                        viewModel.onIntent(Disconnect)
                    } else {
                        requestVpnPermission()
                    }
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
                    onClick = { viewModel.onIntent(ShowListSheet) }
                )
            } ?: PlaceholderCard { viewModel.onIntent(ShowListSheet) }

            if (state.proxySheetState != ProxySheetState.Hidden) {
                ModalBottomSheet(
                    onDismissRequest = { viewModel.onIntent(HiddenSheet) },
                    containerColor = MaterialTheme.colorScheme.background,
                ) {
                    when (state.proxySheetState) {
                        is ProxySheetState.Actions ->
                            ProxyMenuSheetContent(
                                isCurrentProxy = state.currentProxy?.id == state.selectedProxy?.id,
                                isConnected = state.connectionStatus is ProxyConnectionStatus.Connected,
                                isTelegramCompatible = state.selectedProxy?.protocol == ProxyProtocol.MTPROTO || state.selectedProxy?.protocol == ProxyProtocol.SOCKS5,
                                onCopy = { viewModel.onIntent(CopyToClipboard)},
                                onDelete = { viewModel.onIntent(DeleteProxy) },
                                onConnectToTelegram = { viewModel.onIntent(ConnectToTelegramProxy(state.selectedProxy)) },
                                onDisconnect = { viewModel.onIntent(Disconnect) },
                                onDismiss = { viewModel.onIntent(HiddenSheet) }
                            )
                        ProxySheetState.Add ->
                            ProxyAppSheetContent(
                                ip = state.newProxyIp,
                                port = state.newProxyPort,
                                protocol = state.newProxyProtocol,
                                username = state.newProxyUsername,
                                password = state.newProxyPassword,
                                secret = state.newProxySecret,
                                onIpChange = { viewModel.onIntent(ChangeIp(it)) },
                                onPortChange = { viewModel.onIntent(ChangePort(it)) },
                                onProtocolChange = { viewModel.onIntent(ChangeProtocol(it)) },
                                onUsernameChange = { viewModel.onIntent(ChangeUsername(it)) },
                                onPasswordChange = { viewModel.onIntent(ChangePassword(it)) },
                                onSecretChange = { viewModel.onIntent(ChangeSecret(it)) },
                                ipError = state.ipError?.displayName(),
                                portError = state.portError?.displayName(),
                                secretError = state.secretError?.displayName(),
                                usernameError = state.usernameError?.displayName(),
                                passwordError = state.passwordError?.displayName(),
                                onAdd = { viewModel.onIntent(AddProxy) },
                                onReset = { viewModel.onIntent(ResetAddForm) }
                            )
                        ProxySheetState.List -> ProxyListSheetContent(
                            proxies = state.savedProxy,
                            currentProxy = state.currentProxy,
                            onConnectToTelegram = { viewModel.onIntent(ConnectToTelegramProxy(it)) },
                            onSelectProxy = { viewModel.onIntent(SelectProxy(it)) },
                            onMenuOpen = { viewModel.onIntent(ShowActionsSheet(it)) }
                        )
                        ProxySheetState.Hidden -> Unit
                    }
                }
            }
        }
    }
}