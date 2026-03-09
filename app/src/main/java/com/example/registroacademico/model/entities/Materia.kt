package com.example.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "materias")
data class Materia(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val paralelo: String,
    val docenteId: Int
)