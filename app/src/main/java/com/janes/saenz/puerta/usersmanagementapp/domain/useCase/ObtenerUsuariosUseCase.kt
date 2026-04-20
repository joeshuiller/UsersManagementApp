package com.janes.saenz.puerta.usersmanagementapp.domain.useCase

import com.janes.saenz.puerta.usersmanagementapp.data.utils.Resource
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.NetworkRepository
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioDbRepository
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class ObtenerUsuariosUseCase @Inject constructor(
    private val repository: UsuarioRepository,
    private val repositoryDb: UsuarioDbRepository,
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(): Flow<Resource<List<UsuarioDtos>>> = flow {
        emit(Resource.Loading)

        try {
            // 1. Snapshot instantáneo de la base de datos local
            val localDataResource = repositoryDb.observeAllPosts().first()
            val isCacheEmpty = (localDataResource as? Resource.Success)?.data?.isEmpty() != false

            // 2. Lógica de Sincronización (Solo si hay internet)
            if (networkRepository.hasInternetConnection()) {
                if (isCacheEmpty) {
                    // Sincronización forzada: La caché está vacía, necesitamos datos.
                    // Usamos .first() para asegurar que la descarga termine antes de seguir.
                    repository.getUsuarios().first().let { result ->
                        if (result is Resource.Success) {
                            repositoryDb.clearAndInsertPosts(result.data)
                        }
                    }
                }
            } else {
                // 3. Validación de Error Offline
                if (isCacheEmpty) {
                    emit(Resource.Error("No hay internet y la base de datos local está vacía"))
                    return@flow // Salida prematura: No hay nada que observar
                }
            }

            // 4. Fuente de Verdad Única (SSOT)
            // Tanto si hubo red como si no (y hay datos), emitimos el flujo de la DB
            emitAll(repositoryDb.observeAllPosts())
        } catch (e: IOException) {
            emit(Resource.Error("Error de conexión: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)
}