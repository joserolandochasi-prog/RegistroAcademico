package com.example.registroacademico.model.dao

import androidx.room.*
import com.example.registroacademico.model.entities.Estudiante

@Dao
interface EstudianteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEstudiante(estudiante: Estudiante)

    @Query("SELECT * FROM estudiantes")
    suspend fun obtenerEstudiantes(): List<Estudiante>

    @Query("UPDATE estudiantes SET materiaAsignada = :materia, paraleloAsignado = :paralelo WHERE id = :estudianteId")
    suspend fun asignarClase(estudianteId: Int, materia: String, paralelo: String)

    @Query("SELECT * FROM estudiantes WHERE materiaAsignada = :materia AND paraleloAsignado = :paralelo")
    suspend fun obtenerEstudiantesPorClase(materia: String, paralelo: String): List<Estudiante>
}