package com.janes.saenz.puerta.usersmanagementapp.data.network.api

import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.request.UsuarioRequest
import com.janes.saenz.puerta.usersmanagementapp.data.network.dtos.response.UsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioApi {
    @GET("api/users")
    suspend fun getUsuarios(): Response<List<UsuarioResponse>>
    @POST("api/users")
    suspend fun saveUsuario(@Body usuario: UsuarioRequest): Response<Int>
    @GET("api/users/{id}")
    suspend fun getUsusarioiId(@Path("id") id: Int): Response<UsuarioResponse>
}