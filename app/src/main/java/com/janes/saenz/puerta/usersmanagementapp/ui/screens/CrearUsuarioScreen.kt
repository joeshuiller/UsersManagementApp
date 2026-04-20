package com.janes.saenz.puerta.usersmanagementapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.janes.saenz.puerta.usersmanagementapp.ui.UiState.CrearUsuarioUiState
import com.janes.saenz.puerta.usersmanagementapp.ui.viewModels.CrearUsuarioViewModel
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearUsuarioScreen(
    viewModel: CrearUsuarioViewModel = hiltViewModel(),
    onUserCreated: () -> Unit   // Para volver a la lista tras el éxito
) {
    val nombre by viewModel.nombre.collectAsState()
    val email by viewModel.email.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Observar efectos secundarios (Navegar o mostrar Toast en base al estado)
    LaunchedEffect(uiState) {
        when (uiState) {
            is CrearUsuarioUiState.Success -> {
                Toast.makeText(context, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
                onUserCreated() // Disparamos la navegación
            }
            is CrearUsuarioUiState.Error -> {
                val error = (uiState as CrearUsuarioUiState.Error).message
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                viewModel.resetState() // Limpiamos el estado para no mostrar el Toast infinito
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Crear Usuario", style = MaterialTheme.typography.titleLarge)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ingresa los datos del nuevo usuario. Asegúrate de que el correo sea válido.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                HorizontalDivider(modifier = Modifier.height(24.dp))

                // Input de Nombre
                OutlinedTextField(
                    value = nombre,
                    onValueChange = viewModel::onNombreChange,
                    label = { Text("Nombre completo") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text
                    ),
                    // Deshabilitamos escritura si está cargando
                    enabled = uiState !is CrearUsuarioUiState.Loading
                )

                HorizontalDivider(modifier = Modifier.height(16.dp))

                // Input de Correo
                OutlinedTextField(
                    value = email,
                    onValueChange = viewModel::onEmailChange,
                    label = { Text("Correo electrónico") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email // Muestra el "@" en el teclado
                    ),
                    enabled = uiState !is CrearUsuarioUiState.Loading
                )

                HorizontalDivider(modifier = Modifier.height(32.dp))

                // Botón de Guardar
                Button(
                    onClick = { viewModel.guardarUsuario() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = nombre.isNotBlank() && email.isNotBlank() && uiState !is CrearUsuarioUiState.Loading
                ) {
                    if (uiState is CrearUsuarioUiState.Loading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Crear Usuario")
                    }
                }
            }
        }
    }
}