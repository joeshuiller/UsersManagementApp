package com.janes.saenz.puerta.usersmanagementapp.data.mapper

import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.response.UsuarioResponse
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import javax.inject.Inject

class UsuarioResponseMapper @Inject constructor() {
    fun fromResponseToDomain(response: UsuarioResponse): UsuarioDtos {
        return UsuarioDtos(
            id = response.id,
            nombre = response.nombre,
            email = response.email,
            fechaCreacion = response.fechaCreacion
        )
    }
    fun fromResponseListToDomain(responses: List<UsuarioResponse?>?): List<UsuarioDtos> {
        return responses?.filterNotNull()?.map { entity ->
            UsuarioDtos(
                id = entity.id,
                nombre = entity.nombre,
                email = entity.email,
                fechaCreacion = entity.fechaCreacion
            )
        } ?: emptyList()
    }
}