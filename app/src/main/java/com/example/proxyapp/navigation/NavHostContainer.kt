package com.example.proxyapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
            }
            composable(BottomNavRoutes.LIST) {
            }
            composable(BottomNavRoutes.SETTINGS) {
            }
        }
    )
}