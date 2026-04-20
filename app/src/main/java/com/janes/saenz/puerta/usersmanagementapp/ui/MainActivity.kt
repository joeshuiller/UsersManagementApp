package com.janes.saenz.puerta.usersmanagementapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.janes.saenz.puerta.usersmanagementapp.BuildConfig
import com.janes.saenz.puerta.usersmanagementapp.ui.theme.UsersManagementAppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita el diseño de borde a borde para aprovechar toda la pantalla (Edge-to-Edge)
        enableEdgeToEdge()

        // Maneja la transición fluida desde el icono de la app hasta la UI principal
        installSplashScreen()

        // Inicializa Timber para el registro de eventos en modo Debug
        // Nota de Seguridad: En producción, se debe usar un Tree que no exponga PII (Vulnerabilidad 10062)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        setContent {
            UsersManagementAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}