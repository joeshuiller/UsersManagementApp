package com.janes.saenz.puerta.usersmanagementapp.ui.UiState

sealed class CrearUsuarioUiState {
    object Idle : CrearUsuarioUiState()
    object Loading : CrearUsuarioUiState()
    data class Success(val nuevoId: Int) : CrearUsuarioUiState()
    data class Error(val message: String) : CrearUsuarioUiState()
}