package com.example.registroacademico.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.registroacademico.model.entities.Estudiante

@Dao
interface EstudianteDao {

    @Insert
    suspend fun insertarEstudiante(estudiante: Estudiante)

    @Query("SELECT * FROM estudiantes")
    suspend fun obtenerEstudiantes(): List<Estudiante>

}