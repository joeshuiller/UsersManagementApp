package com.janes.saenz.puerta.usersmanagementapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import com.janes.saenz.puerta.usersmanagementapp.domain.useCase.ObtenerUsuarioPorIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallesUsuarioViewModel @Inject constructor(
    private val obtenerUsuarioPorId: ObtenerUsuarioPorIdUseCase // <-- Se inyecta la clase
) : ViewModel() {
    private val _uiState = MutableStateFlow<Resource<UsuarioDtos>>(Resource.Loading)
    val uiState: StateFlow<Resource<UsuarioDtos>> = _uiState.asStateFlow()
    fun cargarDetalles(idUsuario: Int) {
        viewModelScope.launch {
            // Se llama directamente usando los paréntesis (), gracias al 'invoke'
            obtenerUsuarioPorId(idUsuario).collect { resource ->
                _uiState.value = resource
            }
        }
    }
}