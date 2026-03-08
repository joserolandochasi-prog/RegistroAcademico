package com.example.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estudiantes")
data class Estudiante(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val apellido: String
)