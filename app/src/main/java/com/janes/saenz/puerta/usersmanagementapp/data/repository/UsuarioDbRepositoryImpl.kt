package com.janes.saenz.puerta.usersmanagementapp.data.repository

import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.source.UsuarioDbDataSource
import com.janes.saenz.puerta.usersmanagementapp.data.mapper.UsuarioDataBaseMapper
import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class UsuarioDbRepositoryImpl @Inject constructor(
    private val remoteData: UsuarioDbDataSource,
    private val mapper: UsuarioDataBaseMapper
) : BaseRepository(), UsuarioDbRepository {
    override suspend fun clearAndInsertPosts(posts: List<UsuarioDtos>) {
        withContext(Dispatchers.IO) {
            Timber.d("Pantalla abierta: Disparando petición GET de usuarios $posts")
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
}