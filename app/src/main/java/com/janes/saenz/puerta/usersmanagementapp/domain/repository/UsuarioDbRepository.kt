package com.janes.saenz.puerta.usersmanagementapp.domain.repository

import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.entity.UsuarioEntity
import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import kotlinx.coroutines.flow.Flow

interface UsuarioDbRepository {
    suspend fun clearAndInsertPosts(posts: List<UsuarioDtos>)
    fun observeAllPosts(): Flow<Resource<List<UsuarioDtos>>>
    fun observeFilteredPosts(id: Int?, nombre: String?): Flow<Resource<List<UsuarioDtos>>>
    fun getPostById(id: Int): Flow<Resource<UsuarioDtos>>
    fun getPendientesDeSincronizar(): Flow<Resource<List<UsuarioDtos>>>
    suspend fun marcarComoSincronizado(idLocalTemporal: Int, nuevoIdReal: Int)
    suspend fun crearUsuario(posts:UsuarioDtos): Flow<Resource<Int>>
}