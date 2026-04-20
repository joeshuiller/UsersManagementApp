package com.janes.saenz.puerta.usersmanagementapp.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenRoute (
    val ruta: String,
    val title: String,
    val iconSelect: ImageVector,
    val iconNoSelect: ImageVector
) {
    object Home : ScreenRoute("main_screens", "Home",  Icons.Filled.Home, Icons.Outlined.Home)
    object Add : ScreenRoute("create_screens", "Add", Icons.Filled.AddChart, Icons.Outlined.AddChart)
}