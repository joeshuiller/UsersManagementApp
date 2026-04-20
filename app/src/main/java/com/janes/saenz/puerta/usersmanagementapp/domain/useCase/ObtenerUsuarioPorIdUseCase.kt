package com.janes.saenz.puerta.usersmanagementapp.domain.useCase

import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerUsuarioPorIdUseCase @Inject constructor(
    private val repository: UsuarioRepository
) {
    operator fun invoke(id: Int): Flow<Resource<UsuarioDtos>> {
        return repository.getUsusarioiId(id)
    }
}