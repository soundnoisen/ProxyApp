package com.proxyapp.feature.proxy.list.ui.list

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.proxyapp.core.ui.component.SnackBar
import com.proxyapp.domain.model.ThemeMode
import com.proxyapp.feature.proxy.list.R
import com.proxyapp.feature.proxy.list.ui.list.component.ProxyListTopBar
import com.proxyapp.feature.proxy.list.ui.list.component.ProxyListWithBottomActions
import com.proxyapp.feature.proxy.list.ui.list.extensions.displayName
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProxyListScreen(
    currentTheme: ThemeMode,
    viewModel: ProxyListViewModel = hiltViewModel(),
    onNavigateToMain: () -> Unit,
    onNavigateToFilters: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val proxies by viewModel.proxies.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val pullRefreshState = rememberPullToRefreshState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when(effect) {
                is ProxyListEffect.CopyToClipboard -> {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("proxy", effect.text))
                    Toast.makeText(context, R.string.message_proxy_copy, Toast.LENGTH_SHORT).show()
                }
                is ProxyListEffect.NavigateToTelegram -> { context.startActivity(Intent(Intent.ACTION_VIEW, effect.url.toUri()))}
                is ProxyListEffect.NavigateToFilters -> onNavigateToFilters()
                is ProxyListEffect.ShowLoadError -> { snackBarHostState.showSnackbar(effect.error.displayName(context)) }
                is ProxyListEffect.ProxySaved -> { Toast.makeText(context, R.string.message_proxy_saved, Toast.LENGTH_SHORT).show() }
                is ProxyListEffect.NavigateToProxySetup -> onNavigateToMain()
                ProxyListEffect.ProxyRemove -> { Toast.makeText(context, R.string.message_proxy_remove, Toast.LENGTH_SHORT).show() }
            }
        }
    }

    Scaffold(
        topBar = { ProxyListTopBar { viewModel.onIntent(ProxyListIntent.NavigateToFilters) } },
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackBar(snackBarHostState = snackBarHostState, isError = true) }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                )
        ) {
            PullToRefreshBox(
                isRefreshing = state.isLoading,
                onRefresh = { viewModel.onIntent(ProxyListIntent.Refresh) },
                state = pullRefreshState,
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = state.isLoading,
                        containerColor = MaterialTheme.colorScheme.surface,
                        color = MaterialTheme.colorScheme.primary,
                        state = pullRefreshState
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) {
                ProxyListWithBottomActions(
                    proxies = proxies,
                    selectedProxy = state.proxySelected,
                    isSheetVisible = state.isSheetVisible,
                    isSelectedProxySaved = state.isSelectedProxySaved,
                    isLoading = state.isLoading,
                    onLoadNextPage = { viewModel.onIntent(ProxyListIntent.Load) },
                    onCardClick = { viewModel.onIntent(ProxyListIntent.CardClicked(it)) },
                    onMenuOpen = { viewModel.onIntent(ProxyListIntent.OpenMenu(it)) },
                    onDismiss = { viewModel.onIntent(ProxyListIntent.CloseMenu) },
                    onCopy = { viewModel.onIntent(ProxyListIntent.CopyProxy) },
                    onConnect = { viewModel.onIntent(ProxyListIntent.ConnectProxy) },
                    onSave = { viewModel.onIntent(ProxyListIntent.SaveProxy) },
                    onRemove = { viewModel.onIntent(ProxyListIntent.RemoveProxy) },
                    onConnectToTelegram = { viewModel.onIntent(ProxyListIntent.ConnectToTelegramProxy) },
                    currentTheme = currentTheme
                    )
            }
        }
    }
}