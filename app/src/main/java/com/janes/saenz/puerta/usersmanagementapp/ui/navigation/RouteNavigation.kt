package com.janes.saenz.puerta.usersmanagementapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.janes.saenz.puerta.usersmanagementapp.ui.screens.HomeScreens
import com.janes.saenz.puerta.usersmanagementapp.ui.screens.SplashScreen

@Composable
fun RouteNavigation(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(onTransition = { item ->
                navController.navigate(item) {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("main_tabs") {
            HomeScreens()
        }
    }
}