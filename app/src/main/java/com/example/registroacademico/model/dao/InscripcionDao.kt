package com.example.registroacademico.model.dao

import androidx.room.*
import com.example.registroacademico.model.entities.Inscripcion

@Dao
interface InscripcionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inscribir(inscripcion: Inscripcion)

    @Query("SELECT * FROM inscripciones WHERE estudianteId = :estudianteId")
    suspend fun obtenerInscripcionesPorEstudiante(estudianteId: Int): List<Inscripcion>

    @Query("SELECT * FROM inscripciones WHERE materiaId = :materiaId")
    suspend fun obtenerInscripcionesPorMateria(materiaId: Int): List<Inscripcion>

    @Delete
    suspend fun eliminarInscripcion(inscripcion: Inscripcion)
}