package com.janes.saenz.puerta.usersmanagementapp.data.repository

import com.janes.saenz.puerta.usersmanagementapp.data.mapper.UsuarioResponseMapper
import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.request.UsuarioRequest
import com.janes.saenz.puerta.usersmanagementapp.data.network.source.UsuarioDataSource
import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val remoteData: UsuarioDataSource,
    private val mapper: UsuarioResponseMapper
) : BaseRepository(), UsuarioRepository{
    override fun getUsuarios(): Flow<Resource<List<UsuarioDtos>>> {
        return safeApiCallFlow { remoteData.getUsuarios() }.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    Timber.i("Se obtuvieron ${resource.data} usuarios exitosamente")
                    // Conversión de capa de red a capa de dominio
                    val domainList = mapper.fromResponseListToDomain(resource.data)
                    Resource.Success(domainList) // Ahora coincide con la firma del método
                }
                // Propagación transparente de estados
                is Resource.Error -> Resource.Error(resource.message, resource.code)
                is Resource.Loading -> Resource.Loading // O 'Resource.Loading' si lo declaraste como 'object' en tu sealed class
            }
        }
    }

    override  fun saveUsuario(usuario: UsuarioRequest): Flow<Resource<Int>> {
        return safeApiCallFlow { remoteData.saveUsuario(usuario) }.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    // Conversión de capa de red a capa de dominio
                    Resource.Success(resource.data) // Ahora coincide con la firma del método
                }
                // Propagación transparente de estados
                is Resource.Error -> Resource.Error(resource.message, resource.code)
                is Resource.Loading -> Resource.Loading // O 'Resource.Loading' si lo declaraste como 'object' en tu sealed class
            }
        }
    }

    override  fun getUsusarioiId(id: Int): Flow<Resource<UsuarioDtos>> {
        return safeApiCallFlow { remoteData.getUsusarioiId(id) }.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    // Conversión de capa de red a capa de dominio
                    val domainList = mapper.fromResponseToDomain(resource.data)
                    Resource.Success(domainList) // Ahora coincide con la firma del método
                }
                // Propagación transparente de estados
                is Resource.Error -> Resource.Error(resource.message, resource.code)
                is Resource.Loading -> Resource.Loading // O 'Resource.Loading' si lo declaraste como 'object' en tu sealed class
            }
        }
    }
}