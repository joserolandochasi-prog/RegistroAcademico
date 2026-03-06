package com.example.registroacademico.model.entities

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String? = null,
    @SerializedName("rol") val rol: String? = null
)

data class LoginResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: Usuario? = null
)
