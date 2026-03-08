package com.example.registroacademico.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.registroacademico.model.entities.Asistencia

@Dao
interface AsistenciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarAsistencia(asistencia: Asistencia): Long

    @Query("SELECT * FROM asistencia ORDER BY id DESC")
    suspend fun obtenerAsistencias(): List<Asistencia>

    @Query("SELECT * FROM asistencia WHERE id = :id LIMIT 1")
    suspend fun obtenerAsistenciaPorId(id: Int): Asistencia?

    @Query("SELECT * FROM asistencia WHERE docenteId = :docenteId ORDER BY id DESC")
    suspend fun obtenerAsistenciasPorDocente(docenteId: Int): List<Asistencia>

    @Query("DELETE FROM asistencia WHERE id = :id")
    suspend fun eliminarAsistencia(id: Int)
}