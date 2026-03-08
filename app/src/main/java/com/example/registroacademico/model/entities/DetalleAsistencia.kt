package com.example.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detalle_asistencia")
data class DetalleAsistencia(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val asistenciaId: Int,
    val estudianteId: String,
    val presente: Boolean
)