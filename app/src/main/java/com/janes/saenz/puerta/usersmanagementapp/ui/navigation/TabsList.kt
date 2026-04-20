package com.janes.saenz.puerta.usersmanagementapp.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.janes.saenz.puerta.usersmanagementapp.ui.utils.ScreenRoute

@Composable
fun TabsList(navController: NavController) {
    val items = listOf(ScreenRoute.Home, ScreenRoute.Add)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    PrimaryTabRow(
        selectedTabIndex = items.indexOfFirst { it.ruta == rutaActual }.coerceAtLeast(0),
        containerColor = Color(0xFF001526),
        contentColor = Color(0xFFFFFFFF),
        modifier = Modifier.height(80.dp),
    ) {
        items.forEach { item ->
            val select = rutaActual == item.ruta
            Tab(
                selected = select,
                selectedContentColor = Color(0xFFFFFFFF),
                unselectedContentColor = Color.Gray,
                onClick = {
                    // Lógica de navegación optimizada
                    if (rutaActual != item.ruta) {
                        navController.navigate(item.ruta) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                text = { Text(item.title) },
                icon = {
                    Icon(
                        imageVector = if (select) item.iconSelect else item.iconNoSelect,
                        contentDescription = item.title,
                        tint = if (select) Color(0xFFFFFFFF) else Color.Gray
                    )
                }
            )
        }
    }
}