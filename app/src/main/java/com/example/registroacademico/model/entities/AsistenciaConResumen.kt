package com.example.registroacademico.model.entities

import androidx.room.Embedded
import androidx.room.Relation

data class AsistenciaConResumen(
    @Embedded val asistencia: Asistencia,
    @Relation(
        parentColumn = "id",
        entityColumn = "asistenciaId"
    )
    val detalles: List<DetalleAsistencia>
) {
    val totalEstudiantes: Int get() = detalles.size
    val totalAsistieron: Int get() = detalles.count { it.presente }
    val resumenAsistencia: String get() = "$totalAsistieron/$totalEstudiantes"
}