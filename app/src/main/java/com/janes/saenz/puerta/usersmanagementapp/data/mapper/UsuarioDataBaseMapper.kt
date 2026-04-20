package com.janes.saenz.puerta.usersmanagementapp.data.mapper

import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.entity.UsuarioEntity
import com.janes.saenz.puerta.usersmanagementapp.domain.dtos.UsuarioDtos
import javax.inject.Inject
import kotlin.collections.filterNotNull
import kotlin.collections.map

/**
 * PostDataBaseMapper - Componente de mapeo para la capa de persistencia.
 * * Gestiona la conversión bidireccional entre los modelos de la base de datos local
 * y las entidades de la capa de dominio.
 */
class UsuarioDataBaseMapper @Inject constructor() {

    /**
     * Transforma una entidad de Dominio a una entidad de Persistencia (Room).
     * * Uso: Preparar datos para ser insertados o actualizados en la base de datos.
     * * @param domain Objeto de negocio de tipo [Posts].
     * @return Objeto [PostEntity] listo para ser procesado por el DAO.
     */
    fun fromDomainToEntity(domain: UsuarioDtos): UsuarioEntity {
        return UsuarioEntity(
            id = domain.id,
            nombre = domain.nombre,
            email = domain.email,
            fechaCreacion = domain.fechaCreacion
        )
    }

    /**
     * Transforma una entidad de Room a un modelo de Dominio.
     * * Uso: Recuperar datos desde la base de datos para ser consumidos por la lógica de negocio.
     * * @param entity Registro recuperado desde SQLite.
     * @return Entidad [Posts] con su estado y metadatos preservados.
     */
    fun fromEntityToDomain(entity: UsuarioEntity): UsuarioDtos {
        return UsuarioDtos(
            id = entity.id,
            nombre = entity.nombre,
            email = entity.email,
            fechaCreacion = entity.fechaCreacion
        )
    }

    /**
     * Procesa colecciones de entidades de base de datos.
     * * Implementa un filtrado de seguridad para ignorar posibles elementos nulos.
     * * @param entities Lista de registros opcionalmente nulos de la base de datos.
     * @return Lista inmutable de objetos de dominio [Posts].
     */
    fun fromEntityListToDomain(entities: List<UsuarioEntity?>) =
        entities.filterNotNull().map { fromEntityToDomain(it) }
}
