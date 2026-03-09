package com.example.registroacademico.model.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "asistencia")
data class Asistencia(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fecha: String,
    val hora: String = "",
    val materiaId: Int = 0,
    val nombreMateria: String = "",
    
    @Ignore
    val detalles: List<DetalleAsistencia> = emptyList()
) {
    constructor(id: Int, fecha: String, hora: String, materiaId: Int, nombreMateria: String) : 
        this(id, fecha, hora, materiaId, nombreMateria, emptyList())
}
