package com.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "docentes") // <--- La anotación va afuera, sobre la data class
data class Docente(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val email: String,
    val contrasena: String
)