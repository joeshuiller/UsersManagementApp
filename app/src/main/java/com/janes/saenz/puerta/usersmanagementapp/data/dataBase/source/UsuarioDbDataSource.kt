package com.janes.saenz.puerta.usersmanagementapp.data.dataBase.source

import androidx.room.Transaction
import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

interface UsuarioDbDataSource {
    @Transaction
    suspend fun clearAndInsertPosts(posts: List<UsuarioEntity>)
    fun observeAllPosts(): Flow<List<UsuarioEntity?>>
    fun observeFilteredPosts(id: Int?, nombre: String?): Flow<List<UsuarioEntity?>>
    fun getPostById(id: Int): Flow<UsuarioEntity>
    fun getPendientesDeSincronizar(): Flow<List<UsuarioEntity>>
    suspend fun marcarComoSincronizado(idLocalTemporal: Int, nuevoIdReal: Int)
    suspend fun crearUsuario(posts:UsuarioEntity): Long
}