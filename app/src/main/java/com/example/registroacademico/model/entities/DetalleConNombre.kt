package com.example.registroacademico.model.entities

data class DetalleConNombre(
    val estudianteId: Int,
    val nombre: String,
    val apellido: String,
    val presente: Boolean
)