package com.example.proxyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.proxyapp.navigation.Navigation
import com.proxyapp.core.ui.theme.ProxyAppTheme
import com.proxyapp.feature.splash.ui.SplashScreen
import com.proxyapp.feature.splash.ui.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private val themeViewModel: AppThemeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val showSplash by splashViewModel.showSplash.collectAsStateWithLifecycle()
            val themeMode by themeViewModel.themeMode.collectAsStateWithLifecycle()
            ProxyAppTheme(themeMode = themeMode) {
                if (showSplash) {
                    SplashScreen { splashViewModel.finishSplash() }
                } else {
                    Navigation(
                        currentTheme = themeMode,
                        onThemeChange = themeViewModel::changeTheme,
                    )
                }
            }
        }
    }
}