package com.janes.saenz.puerta.usersmanagementapp.data.dataBase.source

import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.entity.UsuarioEntity
import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.repository.UsuarioDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsuarioDbDataSourceImpl @Inject constructor(
    private val apiService: UsuarioDao,
) : UsuarioDbDataSource{
    override suspend fun clearAndInsertPosts(posts: List<UsuarioEntity>) {
        return apiService.clearAndInsertPosts(posts)
    }

    override fun observeAllPosts(): Flow<List<UsuarioEntity?>> {
        return apiService.observeAllPosts()
    }

    override fun observeFilteredPosts(
        id: Int?,
        nombre: String?
    ): Flow<List<UsuarioEntity?>> {
        return apiService.observeFilteredPosts(id, nombre)
    }

    override fun getPostById(id: Int): Flow<UsuarioEntity> {
        return apiService.getPostById(id)
    }

    override fun getPendientesDeSincronizar(): Flow<List<UsuarioEntity>> {
        return apiService.getPendientesDeSincronizar()
    }

    override suspend fun marcarComoSincronizado(
        idLocalTemporal: Int,
        nuevoIdReal: Int
    ) {
        return apiService.marcarComoSincronizado(idLocalTemporal, nuevoIdReal)
    }

    override suspend fun crearUsuario(posts: UsuarioEntity): Long{
        return apiService.insertUsuario(posts)
    }
}