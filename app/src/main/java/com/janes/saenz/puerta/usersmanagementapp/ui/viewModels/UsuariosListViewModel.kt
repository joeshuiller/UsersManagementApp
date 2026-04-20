package com.janes.saenz.puerta.usersmanagementapp.ui.viewModels

import androidx.lifecycle.ViewModel
import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import com.janes.saenz.puerta.usersmanagementapp.domain.useCase.ObtenerUsuariosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.janes.saenz.puerta.usersmanagementapp.domain.useCase.FiltroUsuarioUseCase
import com.janes.saenz.puerta.usersmanagementapp.ui.UiState.UsuariosListUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class UsuariosListViewModel @Inject constructor(
    private val obtenerUsuariosUseCase: ObtenerUsuariosUseCase,
    private val filtroUsuario:FiltroUsuarioUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UsuariosListUiState>(UsuariosListUiState.Idle)
    val uiState: StateFlow<UsuariosListUiState> = _uiState.asStateFlow()
    // Opcional: Cargar automáticamente al instanciar el ViewModel
    init {
        cargarUsuarios()
    }

    fun cargarUsuarios() {
        viewModelScope.launch {
            obtenerUsuariosUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = UsuariosListUiState.Loading
                    }
                    is Resource.Success -> {
                        _uiState.value = UsuariosListUiState.Success(resource.data)
                        Timber.d("Error: ${resource.data}")
                    }
                    is Resource.Error -> {
                        _uiState.value = UsuariosListUiState.Error(
                            resource.message
                        )
                    }
                }
            }
        }
    }
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // Transformamos el texto de búsqueda en el flujo de resultados
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val postsState: StateFlow<Resource<List<UsuarioDtos>>> = _searchQuery
        .debounce(SEARCH_DEBOUNCE_DELAY_MS) // Evita buscar con cada tecla, espera un respiro
        .distinctUntilChanged() // Solo busca si el texto cambió realmente
        .flatMapLatest { query ->
            // Si el query es un número, buscamos por ID, si no por Título
            val id = query.toIntOrNull()
            val title = if (id == null) query else null
            filtroUsuario(id = id, title = title)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Loading
        )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MS = 300L
    }
}