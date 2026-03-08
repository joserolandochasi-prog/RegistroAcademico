package com.example.registroacademico.network

import com.example.registroacademico.model.entities.Estudiante
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("estudiantes")
    suspend fun obtenerEstudiantes(): List<Estudiante>

    @POST("estudiantes")
    suspend fun crearEstudiante(
        @Body estudiante: Estudiante
    ): Estudiante
}