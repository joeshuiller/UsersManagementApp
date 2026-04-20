package com.janes.saenz.puerta.usersmanagementapp.data.network.source

import com.janes.saenz.puerta.usersmanagementapp.data.network.api.UsuarioApi
import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.request.UsuarioRequest
import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.response.UsuarioResponse
import retrofit2.Response
import javax.inject.Inject

class UsuarioDataSourceImpl @Inject constructor(
    private val apiService: UsuarioApi,
) : UsuarioDataSource{
    override suspend fun getUsuarios(): Response<List<UsuarioResponse>> {
        return apiService.getUsuarios()
    }

    override suspend fun saveUsuario(usuario: UsuarioRequest): Response<Int> {
        return apiService.saveUsuario(usuario)
    }

    override suspend fun getUsusarioiId(id: Int): Response<UsuarioResponse> {
        return apiService.getUsusarioiId(id)
    }
}