package com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.response

data class UsuarioResponse(
    val id: Int,
    val nombre: String,
    val email: String,
    val fechaCreacion: String
)