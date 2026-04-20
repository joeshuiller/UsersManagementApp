package com.janes.saenz.puerta.usersmanagementapp.data.network.source

import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.request.UsuarioRequest
import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.response.UsuarioResponse
import retrofit2.Response

interface UsuarioDataSource {
    suspend fun getUsuarios(): Response<List<UsuarioResponse>>
    suspend fun saveUsuario(usuario: UsuarioRequest): Response<Int>
    suspend fun getUsusarioiId(id: Int): Response<UsuarioResponse>
}