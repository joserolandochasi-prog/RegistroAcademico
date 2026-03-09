package com.example.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inscripciones")
data class Inscripcion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val estudianteId: Int,
    val materiaId: Int
)