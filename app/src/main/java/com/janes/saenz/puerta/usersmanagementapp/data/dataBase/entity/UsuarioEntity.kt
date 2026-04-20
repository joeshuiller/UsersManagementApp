package com.janes.saenz.puerta.usersmanagementapp.data.dataBase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class UsuarioEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "nombre")
    val nombre: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "fechaCreacion")
    val fechaCreacion: String,
)