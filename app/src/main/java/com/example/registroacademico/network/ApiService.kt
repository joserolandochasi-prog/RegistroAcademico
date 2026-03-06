package com.example.registroacademico.network

import com.example.registroacademico.model.entities.LoginResponse
import com.example.registroacademico.model.entities.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login.php")
    fun login(@Body user: Usuario): Call<LoginResponse>

    @POST("register.php")
    fun register(@Body user: Usuario): Call<LoginResponse>
}
