package com.example.registroacademico.model.dao

import androidx.room.*
import com.example.registroacademico.model.entities.Asistencia
import com.example.registroacademico.model.entities.AsistenciaConResumen
import kotlinx.coroutines.flow.Flow

@Dao
interface AsistenciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarAsistencia(asistencia: Asistencia): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLista(asistencias: List<Asistencia>)

    @Transaction
    @Query("SELECT * FROM asistencia ORDER BY fecha DESC, hora DESC")
    fun obtenerAsistenciasConResumen(): Flow<List<AsistenciaConResumen>>

    @Query("SELECT * FROM asistencia ORDER BY fecha DESC")
    fun obtenerAsistenciasFlow(): Flow<List<Asistencia>>

    @Query("SELECT * FROM asistencia")
    suspend fun obtenerAsistencias(): List<Asistencia>

    @Delete
    suspend fun eliminarAsistencia(asistencia: Asistencia)
}