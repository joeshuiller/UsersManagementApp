package com.janes.saenz.puerta.usersmanagementapp.data.repository

import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.source.UsuarioDbDataSource
import com.janes.saenz.puerta.usersmanagementapp.data.mapper.UsuarioDataBaseMapper
import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.request.UsuarioRequest
import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.NetworkRepository
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioDbRepository
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.map

class UsuarioDbRepositoryImpl @Inject constructor(
    private val remoteData: UsuarioDbDataSource,
    private val mapper: UsuarioDataBaseMapper,
    private val repository: UsuarioRepository,
    private val networkRepository: NetworkRepository
) : BaseRepository(), UsuarioDbRepository {
    override suspend fun clearAndInsertPosts(posts: List<UsuarioDtos>) {
        withContext(Dispatchers.IO) {
            val entities = posts.map { mapper.fromDomainToEntity(it) }
            remoteData.clearAndInsertPosts(entities)
        }
    }

    override fun observeAllPosts(): Flow<Resource<List<UsuarioDtos>>> {
        return remoteData.observeAllPosts()
            .map { entities -> mapper.fromEntityListToDomain(entities) }
            .asResource()
            .flowOn(Dispatchers.IO)
    }

    override fun observeFilteredPosts(id: Int?, nombre: String?): Flow<Resource<List<UsuarioDtos>>> {
        return remoteData.observeFilteredPosts(id, nombre)
            .map { entities -> mapper.fromEntityListToDomain(entities) }
            .asResource()
            .flowOn(Dispatchers.IO)
    }

    override fun getPostById(id: Int): Flow<Resource<UsuarioDtos>> {
        return remoteData.getPostById(id)
            .map { entity -> mapper.fromEntityToDomain(entity) }
            .asResource()
            .flowOn(Dispatchers.IO)
    }

    override fun getPendientesDeSincronizar(): Flow<Resource<List<UsuarioDtos>>> {
        return remoteData.getPendientesDeSincronizar()
            .map { entities -> mapper.fromEntityListToDomain(entities) }
            .asResource()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun marcarComoSincronizado(
        idLocalTemporal: Int,
        nuevoIdReal: Int
    ) {
        withContext(Dispatchers.IO) {
            remoteData.marcarComoSincronizado(idLocalTemporal, nuevoIdReal)
        }
    }

    override suspend fun crearUsuario(posts: UsuarioDtos): Flow<Resource<Int>> {
        val entity = mapper.fromDomainToEntity(posts).copy(isSynced = false)
        return flow {
            val localDb = remoteData.crearUsuario(entity)
            if (networkRepository.hasInternetConnection()) {
                val data = UsuarioRequest(
                    nombre = entity.nombre,
                    email = entity.email
                )
                repository.saveUsuario(data)
            }
            emit(localDb.toInt())
        }
            .asResource() // ¡Tu extensión le agrega el Loading y el Success automáticamente!
            .flowOn(Dispatchers.IO)

    }
}