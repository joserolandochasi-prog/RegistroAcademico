package com.example.registroacademico.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.registroacademico.model.entities.Estudiante

@Dao
interface EstudianteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEstudiante(estudiante: Estudiante)

    @Query("SELECT * FROM estudiantes ORDER BY id DESC")
    suspend fun obtenerEstudiantes(): List<Estudiante>

    @Query("SELECT * FROM estudiantes WHERE id = :id LIMIT 1")
    suspend fun obtenerEstudiantePorId(id: Int): Estudiante?

    @Update
    suspend fun actualizarEstudiante(estudiante: Estudiante)

    @Delete
    suspend fun eliminarEstudiante(estudiante: Estudiante)

    @Query("DELETE FROM estudiantes WHERE id = :id")
    suspend fun eliminarPorId(id: Int)
}