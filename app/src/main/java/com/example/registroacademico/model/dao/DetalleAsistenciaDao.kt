package com.example.registroacademico.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.registroacademico.model.entities.DetalleAsistencia

@Dao
interface DetalleAsistenciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarDetalle(detalleAsistencia: DetalleAsistencia)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarDetalles(detalles: List<DetalleAsistencia>)

    @Query("SELECT * FROM detalle_asistencia WHERE asistenciaId = :asistenciaId")
    suspend fun obtenerDetallesPorAsistencia(asistenciaId: Int): List<DetalleAsistencia>

    @Query("SELECT * FROM detalle_asistencia WHERE estudianteId = :estudianteId")
    suspend fun obtenerDetallesPorEstudiante(estudianteId: Int): List<DetalleAsistencia>

    @Query("SELECT COUNT(*) FROM detalle_asistencia WHERE estudianteId = :estudianteId AND presente = 0")
    suspend fun contarFaltasPorEstudiante(estudianteId: Int): Int

    @Query("SELECT COUNT(*) FROM detalle_asistencia WHERE estudianteId = :estudianteId")
    suspend fun contarTotalAsistenciasPorEstudiante(estudianteId: Int): Int

    @Query("DELETE FROM detalle_asistencia WHERE id = :id")
    suspend fun eliminarDetalle(id: Int)
}