package com.example.proxyapp.navigation

import com.example.proxyapp.R

object BottomNavRoutes {
    const val SETUP = "proxy_setup"
    const val LIST = "proxy_list"
    const val SETTINGS = "proxy_settings"
}

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
)

object BottomNavItems {
    val items = listOf(
        BottomNavItem(BottomNavRoutes.SETUP, R.drawable.ic_proxy_setup, "Setup"),
        BottomNavItem(BottomNavRoutes.LIST, R.drawable.ic_proxy_list, "List"),
        BottomNavItem(BottomNavRoutes.SETTINGS, R.drawable.ic_proxy_settings, "Settings"),
    )
}