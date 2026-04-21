package com.janes.saenz.puerta.usersmanagementapp.domain.useCase

import android.annotation.SuppressLint
import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.request.UsuarioRequest
import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioDbRepository
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CrearUsuarioUseCase @Inject constructor(
    private val repository: UsuarioDbRepository
) {
    @SuppressLint("NewApi")
    suspend operator fun invoke(usuario: UsuarioRequest): Flow<Resource<Int>> {
        // 1. Validaciones puras de Dominio (Evitamos gastar red si los datos ya están mal)
        if (usuario.nombre.isBlank()) {
            return flowOf(Resource.Error("El nombre no puede estar vacío"))
        }

        if (!usuario.email.contains("@")) {
            return flowOf(Resource.Error("El formato del correo es inválido"))
        }

        // 2. Fecha moderna (Sin importar la versión de Android)
        val fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        // 3. Modelo de Dominio Puro (Cero DTOs)
        val nuevoUsuario = UsuarioDtos(
            id = 0,
            nombre = usuario.nombre.trim(),
            email = usuario.email.trim(),
            fechaCreacion = fechaHoy
        )

        // 2. Si todo está correcto, delegamos la responsabilidad al repositorio
        return repository.crearUsuario(nuevoUsuario)
    }
}