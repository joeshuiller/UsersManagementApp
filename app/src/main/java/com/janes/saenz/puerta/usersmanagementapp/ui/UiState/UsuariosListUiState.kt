package com.janes.saenz.puerta.usersmanagementapp.ui.UiState

import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos

sealed class UsuariosListUiState {
    object Idle : UsuariosListUiState()
    object Loading : UsuariosListUiState()
    // Aquí recibimos una Lista de Usuarios
    data class Success(val usuarios: List<UsuarioDtos>) : UsuariosListUiState()
    data class Error(val message: String) : UsuariosListUiState()
}