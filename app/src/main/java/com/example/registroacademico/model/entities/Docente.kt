package com.example.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "docentes")
data class Docente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String
)