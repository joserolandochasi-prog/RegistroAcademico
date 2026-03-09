package com.example.registroacademico.model.dao

import androidx.room.*
import com.example.registroacademico.model.entities.Curso

@Dao
interface CursoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCurso(curso: Curso)

    @Query("SELECT * FROM cursos")
    suspend fun obtenerCursos(): List<Curso>

    @Delete
    suspend fun eliminarCurso(curso: Curso)
}