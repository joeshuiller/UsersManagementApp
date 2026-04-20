package com.janes.saenz.puerta.usersmanagementapp.core

import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.source.UsuarioDbDataSource
import com.janes.saenz.puerta.usersmanagementapp.data.dataBase.source.UsuarioDbDataSourceImpl
import com.janes.saenz.puerta.usersmanagementapp.data.network.source.UsuarioDataSource
import com.janes.saenz.puerta.usersmanagementapp.data.network.source.UsuarioDataSourceImpl
import com.janes.saenz.puerta.usersmanagementapp.data.repository.AndroidNetworkRepositoryImpl
import com.janes.saenz.puerta.usersmanagementapp.data.repository.UsuarioDbRepositoryImpl
import com.janes.saenz.puerta.usersmanagementapp.data.repository.UsuarioRepositoryImpl
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.NetworkRepository
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioDbRepository
import com.janes.saenz.puerta.usersmanagementapp.domain.repository.UsuarioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/**
 * DataModule - Módulo de resolución de interfaces para la capa de datos.
 *
 * Este módulo vincula las interfaces de repositorio y fuentes de datos con sus
 * implementaciones reales, permitiendo el uso de Inyección de Dependencias
 * bajo los principios SOLID.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    // --- REPOSITORIOS (Domain Interfaces) ---

    @Binds
    @Singleton
    abstract fun bindNetworkRepository(
        impl: AndroidNetworkRepositoryImpl
    ): NetworkRepository

    @Binds
    @Singleton
    abstract fun bindUsuarioRepository(
        impl: UsuarioRepositoryImpl
    ): UsuarioRepository

    @Binds
    @Singleton
    abstract fun bindUsuarioDbRepository(
        impl: UsuarioDbRepositoryImpl
    ): UsuarioDbRepository

    // --- DATA SOURCES (Infrastructure) ---

    @Binds
    @Singleton
    abstract fun bindUsuarioDataSource(
        impl: UsuarioDataSourceImpl
    ): UsuarioDataSource

    @Binds
    @Singleton
    abstract fun bindUsuarioDbDataSource(
        impl: UsuarioDbDataSourceImpl
    ): UsuarioDbDataSource
}
