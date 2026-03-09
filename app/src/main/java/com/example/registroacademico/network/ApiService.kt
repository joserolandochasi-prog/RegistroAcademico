package com.example.registroacademico.network

import com.example.registroacademico.model.entities.Asistencia
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("asistencias")
    suspend fun obtenerAsistencias(): List<Asistencia>

    @POST("asistencias")
    suspend fun guardarAsistencia(
        @Body asistencia: Asistencia
    ): Response<Asistencia>

}