package com.example.registroacademico.repository

import com.example.registroacademico.model.dao.AsistenciaDao
import com.example.registroacademico.model.entities.Asistencia

class AsistenciaRepository(
    private val asistenciaDao: AsistenciaDao
) {

    suspend fun guardar(asistencia: Asistencia) {
        asistenciaDao.insertarAsistencia(asistencia)
    }

    suspend fun obtenerAsistencias(): List<Asistencia> {
        return asistenciaDao.obtenerAsistencias()
    }
}