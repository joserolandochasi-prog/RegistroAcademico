package com.example.registroacademico.model.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.registroacademico.model.entities.Estudiante

@Dao
interface EstudianteDao {
    @Query("SELECT * FROM estudiantes")
    suspend fun getAllEstudiantes(): List<Estudiante>
}