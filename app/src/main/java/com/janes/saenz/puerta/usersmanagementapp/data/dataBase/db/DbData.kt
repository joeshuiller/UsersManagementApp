package com.janes.saenz.puerta.usersmanagementapp.data.dataBase.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.entity.UsuarioEntity
import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.repository.UsuarioDao

/**
 * DbData - Definición central y configurador del motor de persistencia Room.
 * * Esta clase orquesta el esquema de la base de datos local y provee los DAOs
 * necesarios para la manipulación de datos.
 *
 * @property entities Lista de clases marcadas con @Entity que definen las tablas de la DB.
 * @property version Versión actual del esquema (incremental para migraciones).
 * @property exportSchema Determina si se genera un archivo JSON con el esquema (útil para auditoría).
 */
@Database(
    entities = [
        UsuarioEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class DbData : RoomDatabase() {

    /** * Acceso a operaciones CRUD y observación de publicaciones.
     * @return [PostDao]
     */
    abstract fun usuarioDao(): UsuarioDao
}
