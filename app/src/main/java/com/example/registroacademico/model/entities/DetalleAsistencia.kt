package com.example.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "detalle_asistencia",
    foreignKeys = [
        ForeignKey(
            entity = Asistencia::class,
            parentColumns = ["id"],
            childColumns = ["asistenciaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Estudiante::class,
            parentColumns = ["id"],
            childColumns = ["estudianteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class DetalleAsistencia(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val asistenciaId: Int,

    val estudianteId: Int,

    val presente: Boolean
)