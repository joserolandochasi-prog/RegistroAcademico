package com.example.registroacademico.repository

import com.example.registroacademico.model.dao.AsistenciaDao
import com.example.registroacademico.model.dao.DetalleAsistenciaDao
import com.example.registroacademico.model.entities.Asistencia
import com.example.registroacademico.model.entities.DetalleAsistencia

class AsistenciaRepository(
    private val asistenciaDao: AsistenciaDao,
    private val detalleAsistenciaDao: DetalleAsistenciaDao
) {

    suspend fun guardarAsistenciaConDetalles(
        asistencia: Asistencia,
        detalles: List<DetalleAsistencia>
    ) {
        val asistenciaId = asistenciaDao.insertarAsistencia(asistencia).toInt()

        val detallesConId = detalles.map { detalle ->
            detalle.copy(asistenciaId = asistenciaId)
        }

        detalleAsistenciaDao.insertarDetalles(detallesConId)
    }

    suspend fun obtenerFaltasPorEstudiante(estudianteId: Int): Int {
        return detalleAsistenciaDao.contarFaltasPorEstudiante(estudianteId)
    }

    suspend fun obtenerTotalPorEstudiante(estudianteId: Int): Int {
        return detalleAsistenciaDao.contarTotalAsistenciasPorEstudiante(estudianteId)
    }

    suspend fun obtenerDetallesPorEstudiante(estudianteId: Int): List<DetalleAsistencia> {
        return detalleAsistenciaDao.obtenerDetallesPorEstudiante(estudianteId)
    }
}