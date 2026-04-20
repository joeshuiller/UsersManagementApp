package com.janes.saenz.puerta.usersmanagementapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.janes.saenz.puerta.usersmanagementapp.ui.screens.CrearUsuarioScreen
import com.janes.saenz.puerta.usersmanagementapp.ui.screens.DetailsScreens
import com.janes.saenz.puerta.usersmanagementapp.ui.screens.UsuariosListScreen
import com.janes.saenz.puerta.usersmanagementapp.ui.utils.ScreenRoute


@Composable
fun NavHostContent(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = ScreenRoute.Home.ruta// Punto de entrada
    ) {
        composable(ScreenRoute.Home.ruta) {
            UsuariosListScreen(
                onUsuarioClick = { id ->
                    navController.navigate("details/$id"){
                        popUpTo("details") { inclusive = true }
                    }
                }
            )
        }
        composable(ScreenRoute.Add.ruta) {
            CrearUsuarioScreen(
                onUserCreated = {
                    navController.navigate(ScreenRoute.Home.ruta)
                }
            )
        }

        composable(
            route ="details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailsScreens(
                id = id.toInt(),
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}