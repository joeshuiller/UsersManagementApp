package com.janes.saenz.puerta.usersmanagementapp.domain.useCase

import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.request.UsuarioRequest
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
    operator fun invoke(): Flow<Resource<List<UsuarioDtos>>> = flow { // 1. Devolvemos Dominio puro, NO Dtos
        emit(Resource.Loading)

        try {
            // --- PASO 2: REVISAR ESTADO DE LA CACHÉ ---
            val localCacheResource = repositoryDb.observeAllPosts().first { it !is Resource.Loading }
            val isCacheEmpty = localCacheResource is Resource.Success && localCacheResource.data.isEmpty()

            // --- PASO 3: TRAER DATOS NUEVOS (Si hay internet) ---
            if (networkRepository.hasInternetConnection()) {
                if (isCacheEmpty) {
                    Timber.d("Caché vacía. Descargando datos frescos de la API...")

                    repository.getUsuarios().first { it !is Resource.Loading }.let { resultApi ->
                        if (resultApi is Resource.Success) {
                            repositoryDb.clearAndInsertPosts(resultApi.data)
                            Timber.d("Base de datos local poblada exitosamente.")
                        } else if (resultApi is Resource.Error) {
                            emit(Resource.Error("Error al descargar usuarios: ${resultApi.message}"))
                        }
                    }
                }
                // Tip Senior: ¿Y si la caché NO está vacía pero hay internet?
                // Podrías descargar igual los datos en segundo plano para actualizar la lista local.
            } else {
                // --- PASO 4: MANEJO DE ERROR OFFLINE ---
                if (isCacheEmpty) {
                    emit(Resource.Error("No hay internet y la base de datos local está vacía. No hay nada que mostrar."))
                    return@flow // Abortamos porque no podemos emitir la base de datos vacía.
                }
            }

            // --- PASO 5: LA ÚNICA FUENTE DE VERDAD ---
            // Emitimos TODO lo que suceda de ahora en adelante en la base de datos local
            emitAll(repositoryDb.observeAllPosts())

        } catch (e: Exception) { // Atrapamos Exception general, no solo IOException
            Timber.e(e, "Error crítico en el flujo de orquestación de usuarios")
            emit(Resource.Error("Ocurrió un error inesperado: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)
}