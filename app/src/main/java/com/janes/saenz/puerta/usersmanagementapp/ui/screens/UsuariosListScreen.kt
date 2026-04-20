package com.janes.saenz.puerta.usersmanagementapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import com.janes.saenz.puerta.usersmanagementapp.ui.UiState.UsuariosListUiState
import com.janes.saenz.puerta.usersmanagementapp.ui.component.EmptyStateComponent
import com.janes.saenz.puerta.usersmanagementapp.ui.component.SearchTopAppBar
import com.janes.saenz.puerta.usersmanagementapp.ui.component.ShowError
import com.janes.saenz.puerta.usersmanagementapp.ui.component.ShowSkeleton
import com.janes.saenz.puerta.usersmanagementapp.ui.component.UsuarioItem
import com.janes.saenz.puerta.usersmanagementapp.ui.viewModels.UsuariosListViewModel
import timber.log.Timber

@Composable
fun UsuariosListScreen(
    viewModel: UsuariosListViewModel = hiltViewModel(),
    onUsuarioClick: (Int) -> Unit // Callback para navegar al detalle
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var isSearchActive by remember { mutableStateOf(false) }

    when (uiState) {
        is UsuariosListUiState.Idle -> {
            // Estado inicial
        }
        is UsuariosListUiState.Loading -> {
            ShowSkeleton()
        }

        is UsuariosListUiState.Error -> {
            val mensaje = (uiState as UsuariosListUiState.Error).message
            EmptyStateComponent(
                title = "Ocurrió un problema",
                message = mensaje,
                icon = Icons.Default.Warning, // Requiere importar el ícono Warning
                actionText = "Reintentar",
                onAction = { viewModel.cargarUsuarios() }
            )
        }

        is UsuariosListUiState.Success -> {
            val usuarios = (uiState as UsuariosListUiState.Success).usuarios
            if (usuarios.isEmpty()) {
                EmptyStateComponent(
                    title = "No hay dato",
                    message = "no hay datos registrados",
                    icon = Icons.Default.Warning, // Requiere importar el ícono Warning
                    actionText = "Reintentar",
                    onAction = { viewModel.cargarUsuarios() }
                )
            } else {
                HomeScaffoldContent(
                    uiState = uiState,
                    searchQuery = searchQuery,
                    isSearchActive = isSearchActive,
                    onSearchToggle = { isSearchActive = it },
                    onQueryChanged = { viewModel.onSearchQueryChanged(it) },
                    onRetryFilter = { viewModel.onSearchQueryChanged(searchQuery) },
                    onClick = onUsuarioClick
                )
            }
        }
    }
}
/**
 * Contenedor estructural que define la anatomía visual de la pantalla de inicio.
 *
 * Implementa un [Scaffold] que integra una barra de búsqueda reactiva y un
 * contenedor principal para los resultados filtrados.
 *
 * @param uiState Estado actual de la lista de posts filtrados.
 * @param searchQuery Texto actual presente en el campo de búsqueda.
 * @param isSearchActive Define si el buscador está en modo expandido o colapsado.
 * @param onSearchToggle Callback para cambiar la visibilidad del buscador.
 * @param onQueryChanged Callback que se dispara al escribir en el buscador.
 * @param onRetryFilter Acción para reintentar la búsqueda en caso de error.
 * @param onClick Navegación hacia el detalle del recurso.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScaffoldContent(
    uiState: UsuariosListUiState,
    searchQuery: String,
    isSearchActive: Boolean,
    onSearchToggle: (Boolean) -> Unit,
    onQueryChanged: (String) -> Unit,
    onRetryFilter: () -> Unit,
    onClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            SearchTopAppBar(
                isSearchActive = isSearchActive,
                searchQuery = searchQuery,
                onSearchToggle = onSearchToggle,
                onQueryChanged = onQueryChanged
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (uiState) {
                is UsuariosListUiState.Loading -> ShowSkeleton()
                is UsuariosListUiState.Error -> ShowError(uiState.message) { onRetryFilter() }
                is UsuariosListUiState.Success -> {
                    val usuarios = uiState.usuarios
                    if (usuarios.isEmpty()) {
                        EmptyStateComponent(
                            title = "No hay dato",
                            message = "no hay datos registrados",
                            icon = Icons.Default.Warning, // Requiere importar el ícono Warning
                            actionText = "Reintentar",
                            onAction = { onQueryChanged("") }
                        )
                    } else {
                        HomeList(
                            usuarios = usuarios,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
                }

                UsuariosListUiState.Idle -> {}
            }
        }
    }
}

/**
 * Representación optimizada de la lista de publicaciones.
 *
 * Utiliza un [LazyColumn] para garantizar un rendimiento fluido incluso con listas
 * extensas, manejando el reciclaje de vistas de forma eficiente.
 *
 * @param UsuarioDtos Colección de objetos [List<UsuarioDtos>] a renderizar.
 * @param paddingValues Márgenes de seguridad proporcionados por el Scaffold.
 * @param onClick Acción a ejecutar al presionar un elemento de la lista.
 */
@Composable
private fun HomeList(
    usuarios: List<UsuarioDtos>,
    paddingValues: PaddingValues,
    onClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(usuarios) { usuario ->
            UsuarioItem(
                usuario = usuario,
                onClick = { onClick(it) },
                paddingValues = paddingValues
            )
        }
    }
}
