package com.example.proxyapp.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.proxyapp.domain.model.ThemeMode

@Composable
fun Navigation(
    currentTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit) {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomNavController)
        }, content = { padding ->
            NavHostContainer(
                navController = bottomNavController,
                padding = padding,
                currentTheme = currentTheme,
                onThemeChange = onThemeChange
            )
        }
    )
}