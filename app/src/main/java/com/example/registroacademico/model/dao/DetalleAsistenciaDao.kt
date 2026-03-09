package com.example.registroacademico.model.dao

import androidx.room.*
import com.example.registroacademico.model.entities.DetalleAsistencia
import com.example.registroacademico.model.entities.DetalleConNombre
import kotlinx.coroutines.flow.Flow

@Dao
interface DetalleAsistenciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarDetalle(detalle: DetalleAsistencia)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLista(detalles: List<DetalleAsistencia>)

    @Query("""
        SELECT d.estudianteId, e.nombre, e.apellido, d.presente 
        FROM detalle_asistencia d 
        JOIN estudiantes e ON d.estudianteId = e.id 
        WHERE d.asistenciaId = :asistenciaId
    """)
    suspend fun obtenerDetallesConNombre(asistenciaId: Int): List<DetalleConNombre>

    @Query("""
        SELECT * FROM detalle_asistencia 
        WHERE estudianteId = :estudianteId
    """)
    fun obtenerPorEstudiante(estudianteId: Int): Flow<List<DetalleAsistencia>>

    @Query("""
        SELECT * FROM detalle_asistencia 
        WHERE asistenciaId = :asistenciaId
    """)
    fun obtenerPorAsistencia(asistenciaId: Int): Flow<List<DetalleAsistencia>>
    
    @Query("SELECT COUNT(*) FROM detalle_asistencia WHERE estudianteId = :estudianteId")
    suspend fun totalClasesEstudiante(estudianteId: Int): Int

    @Query("SELECT COUNT(*) FROM detalle_asistencia WHERE estudianteId = :estudianteId AND presente = 1")
    suspend fun totalAsistenciasEstudiante(estudianteId: Int): Int
}