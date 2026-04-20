package com.janes.saenz.puerta.usersmanagementapp.ui.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.request.UsuarioRequest
import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.useCase.CrearUsuarioUseCase
import com.janes.saenz.puerta.usersmanagementapp.ui.UiState.CrearUsuarioUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrearUsuarioViewModel @Inject constructor(
    private val crearUsuarioUseCase: CrearUsuarioUseCase
) : ViewModel() {

    // --- ESTADO DEL FORMULARIO ---
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    // --- ESTADO DE LA RED ---
    private val _uiState = MutableStateFlow<CrearUsuarioUiState>(CrearUsuarioUiState.Idle)
    val uiState: StateFlow<CrearUsuarioUiState> = _uiState.asStateFlow()

    // Eventos de la UI
    fun onNombreChange(nuevoNombre: String) { _nombre.value = nuevoNombre }
    fun onEmailChange(nuevoEmail: String) { _email.value = nuevoEmail }

    fun guardarUsuario() {
        viewModelScope.launch {
            _uiState.value = CrearUsuarioUiState.Loading
            val usuario = UsuarioRequest(
                _nombre.value,
                _email.value
            )
            crearUsuarioUseCase(usuario).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = CrearUsuarioUiState.Loading
                    }
                    is Resource.Success -> {
                        _uiState.value = CrearUsuarioUiState.Success(resource.data ?: 0)
                    }
                    is Resource.Error -> {
                        _uiState.value = CrearUsuarioUiState.Error(
                            resource.message ?: "Ocurrió un error al crear el usuario"
                        )
                    }
                }
            }
        }
    }

    // Para limpiar el error si el usuario decide volver a intentar
    fun resetState() {
        _uiState.value = CrearUsuarioUiState.Idle
    }
}