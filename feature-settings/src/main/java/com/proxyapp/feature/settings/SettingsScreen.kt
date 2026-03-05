package com.proxyapp.feature.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.proxyapp.core.common.AppUrls
import com.proxyapp.core.common.openUrl
import com.proxyapp.domain.model.ThemeMode
import com.proxyapp.feature.settings.component.AboutSection
import com.proxyapp.feature.settings.component.SettingsTopBar
import com.proxyapp.feature.settings.component.ThemeSelection
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreen(
    currentTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when(effect) {
                SettingsEffect.NavigateToSource -> {
                    context.openUrl(AppUrls.GITHUB)
                }
            }
        }
    }
    
    Scaffold(
        topBar = { SettingsTopBar() },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            item {
                ThemeSelection(
                    currentTheme = currentTheme,
                    onSetTheme = { onThemeChange(it) }
                )
                Spacer(Modifier.height(24.dp))
                AboutSection(
                    onSourceClick = { viewModel.onIntent(SettingsIntent.SourceClicked) }
                )
            }
        }
    }
}