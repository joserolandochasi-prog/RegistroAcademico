package com.example.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "asistencia",
    foreignKeys = [
        ForeignKey(
            entity = Docente::class,
            parentColumns = ["id"],
            childColumns = ["docenteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Asistencia(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fecha: String,
    val docenteId: Int
)