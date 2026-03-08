package com.example.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String
)