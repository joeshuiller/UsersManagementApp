package com.janes.saenz.puerta.usersmanagementapp.domain.repository

import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.request.UsuarioRequest
import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {
    fun getUsuarios(): Flow<Resource<List<UsuarioDtos>>>
    fun saveUsuario(usuario: UsuarioRequest): Flow<Resource<Int>>
    fun getUsusarioiId(id: Int): Flow<Resource<UsuarioDtos>>
}