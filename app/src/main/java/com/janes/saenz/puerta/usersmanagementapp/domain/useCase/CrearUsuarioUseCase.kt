package com.janes.saenz.puerta.usersmanagementapp.domain.useCase

import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.request.UsuarioRequest
import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CrearUsuarioUseCase @Inject constructor(
    private val repository: UsuarioRepository
) {
    operator fun invoke(usuario: UsuarioRequest): Flow<Resource<Int>> {
        // 1. Validaciones puras de Dominio (Evitamos gastar red si los datos ya están mal)
        if (usuario.nombre.isBlank()) {
            return flowOf(Resource.Error("El nombre no puede estar vacío"))
        }

        if (!usuario.email.contains("@")) {
            return flowOf(Resource.Error("El formato del correo es inválido"))
        }

        // 2. Si todo está correcto, delegamos la responsabilidad al repositorio
        return repository.saveUsuario(usuario)
    }
}