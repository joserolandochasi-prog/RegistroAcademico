package com.example.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cursos") // <--- ¡ESTO ES LO QUE FALTA!
data class Curso(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val seccion: String
)