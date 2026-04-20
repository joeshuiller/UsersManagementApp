package com.janes.saenz.puerta.usersmanagementapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.janes.saenz.puerta.usersmanagementapp.ui.navigation.NavHostContent
import com.janes.saenz.puerta.usersmanagementapp.ui.navigation.TabsList

@Composable
fun HomeScreens() {
    val navTabController = rememberNavController()
    Scaffold(
        bottomBar = {
            TabsList(navTabController)
        }
    ) { paddingValues ->
        NavHostContent(
            navController = navTabController,
            modifier = Modifier.padding(paddingValues),
        )
    }
}