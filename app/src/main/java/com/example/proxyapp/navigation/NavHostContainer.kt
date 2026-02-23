package com.example.proxyapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.proxyapp.feature.proxy.list.ui.filters.ProxyFiltersScreen
import com.proxyapp.feature.proxy.list.ui.list.ProxyListScreen
import com.proxyapp.feature.proxy.setup.ui.ProxySetupScreen

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavRoutes.SETUP,
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable(BottomNavRoutes.SETUP) {
                ProxySetupScreen()
            }
            composable(BottomNavRoutes.LIST) {
                ProxyListScreen(
                    onNavigateToFilters = { navController.navigate(NavRoutes.FILTERS) },
                    onNavigateToMain = { navController.navigate(BottomNavRoutes.SETUP) }
                )
            }
            composable(BottomNavRoutes.SETTINGS) {
            }
            composable( NavRoutes.FILTERS) {
                ProxyFiltersScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    )
}