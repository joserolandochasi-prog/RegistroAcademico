package com.example.registroacademico.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.registroacademico.model.entities.Asistencia

@Dao
interface AsistenciaDao {

    @Insert
    suspend fun insertarAsistencia(asistencia: Asistencia)

    @Query("SELECT * FROM asistencia")
    suspend fun obtenerAsistencias(): List<Asistencia>
}