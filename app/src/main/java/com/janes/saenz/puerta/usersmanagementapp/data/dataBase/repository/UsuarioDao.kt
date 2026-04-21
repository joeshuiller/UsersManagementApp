package com.janes.saenz.puerta.usersmanagementapp.data.dataBase.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    /**
     * Recupera una publicación específica por su identificador.
     * @return [Flow] que emite la entidad cada vez que se actualiza en DB.
     */
    @Query("SELECT * FROM users_table WHERE id = :id")
    fun getPostById(id: Int): Flow<UsuarioEntity>

    /**
     * Observa todas las publicaciones ordenadas de forma ascendente.
     * @return [Flow] con la lista completa de publicaciones.
     */
    @Query("SELECT * FROM users_table ORDER BY id ASC")
    fun observeAllPosts(): Flow<List<UsuarioEntity?>>

    /**
     * Inserta o actualiza una lista de publicaciones.
     * Usa [OnConflictStrategy.REPLACE] para sobrescribir datos antiguos con los nuevos.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<UsuarioEntity>)

    /**
     * Inserta un solo usuario y devuelve el ID local (rowId) que Room le acaba de asignar.
     * Al devolver Long, Room sabe que quieres el ID autogenerado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: UsuarioEntity): Long

    /**
     * Realiza una búsqueda filtrada reactiva.
     * * Soporta filtrado por ID exacto y/o coincidencia parcial en el título.
     * * Si los parámetros son nulos, se ignoran en la cláusula WHERE.
     */
    @Query(
        """
        SELECT * FROM users_table 
        WHERE (:id IS NULL OR id = :id)
        AND (:nombre IS NULL OR nombre LIKE '%' || :nombre || '%')
        ORDER BY id ASC
    """
    )
    fun observeFilteredPosts(id: Int?, nombre: String?): Flow<List<UsuarioEntity?>>

    /**
     * Elimina registros que ya no son válidos (no presentes en la última sincronización).
     * @param remainingIds Lista de IDs que deben permanecer en la base de datos.
     */
    @Query("DELETE FROM users_table WHERE id NOT IN (:remainingIds)")
    suspend fun deleteOrphans(remainingIds: List<Int>)

    /**
     * Ejecuta una sincronización diferencial atómica.
     * 1. Actualiza/Inserta los datos recibidos.
     * 2. Elimina los datos locales que ya no existen en la fuente remota.
     * * [Transaction] asegura que toda la operación sea 'Todo o Nada'.
     */
    @Transaction
    suspend fun clearAndInsertPosts(posts: List<UsuarioEntity>) {
        insertPosts(posts)
        val ids = posts.map { it.id }
        deleteOrphans(ids)
    }

    // Obtener solo los que el backend no conoce
    @Query("SELECT * FROM users_table WHERE isSynced = 0")
    fun getPendientesDeSincronizar(): Flow<List<UsuarioEntity>>

    @Transaction
    // Actualizar el registro cuando el backend nos devuelva el ID real
    @Query("UPDATE users_table SET id = :nuevoIdReal, isSynced = 1 WHERE id = :idLocalTemporal")
    suspend fun marcarComoSincronizado(idLocalTemporal: Int, nuevoIdReal: Int)
}