package com.example.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asistencia")
data class Asistencia(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val fecha: String
)