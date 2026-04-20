package com.janes.saenz.puerta.usersmanagementapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.janes.saenz.puerta.usersmanagementapp.ui.navigation.RouteNavigation

@Composable
fun AppNavigation(
    modifier: Modifier
) {
    val navController = rememberNavController()
    RouteNavigation(navController, modifier)
}