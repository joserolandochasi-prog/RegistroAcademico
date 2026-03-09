package com.example.registroacademico.repository

import com.example.registroacademico.network.ApiService
import com.example.registroacademico.model.dao.AsistenciaDao
import com.example.registroacademico.model.dao.DetalleAsistenciaDao
import com.example.registroacademico.model.entities.Asistencia
import com.example.registroacademico.model.entities.AsistenciaConResumen
import com.example.registroacademico.model.entities.DetalleAsistencia
import com.example.registroacademico.model.entities.AsistenciaEstudianteInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AsistenciaRepository(
    private val apiService: ApiService,
    private val asistenciaDao: AsistenciaDao,
    private val detalleAsistenciaDao: DetalleAsistenciaDao
) {
    val asistenciasConResumen: Flow<List<AsistenciaConResumen>> = asistenciaDao.obtenerAsistenciasConResumen()

    val todasLasAsistencias: Flow<List<Asistencia>> = asistenciaDao.obtenerAsistenciasFlow()

    fun obtenerFaltasEstudiante(estudianteId: Int): Flow<List<Asistencia>> {
        return asistenciasConResumen.map { lista ->
            lista.filter { resumen ->
                resumen.detalles.any { it.estudianteId == estudianteId && !it.presente }
            }.map { it.asistencia }
        }
    }

    fun obtenerHistorialEstudiante(estudianteId: Int): Flow<List<AsistenciaEstudianteInfo>> {
        return asistenciasConResumen.map { lista ->
            lista.filter { resumen ->
                resumen.detalles.any { it.estudianteId == estudianteId }
            }.map { resumen ->
                val detalle = resumen.detalles.first { it.estudianteId == estudianteId }
                AsistenciaEstudianteInfo(
                    fecha = resumen.asistencia.fecha,
                    hora = resumen.asistencia.hora,
                    nombreMateria = resumen.asistencia.nombreMateria,
                    presente = detalle.presente
                )
            }
        }
    }

    suspend fun refrescarAsistencias() {
        withContext(Dispatchers.IO) {
            try {
                val respuestaApi = apiService.obtenerAsistencias()
                asistenciaDao.insertarLista(respuestaApi)
            } catch (e: Exception) {
                throw Exception("Error de red al sincronizar asistencias: ${e.message}")
            }
        }
    }

    suspend fun registrarAsistenciaCompleta(asistencia: Asistencia, detalles: List<DetalleAsistencia>) {
        withContext(Dispatchers.IO) {
            val asistenciaId = asistenciaDao.insertarAsistencia(asistencia).toInt()
            if (asistenciaId > 0) {
                val detallesConId = detalles.map { it.copy(asistenciaId = asistenciaId) }
                detalleAsistenciaDao.insertarLista(detallesConId)
                
                val asistenciaParaApi = asistencia.copy(id = asistenciaId, detalles = detallesConId)
                try {
                    apiService.guardarAsistencia(asistenciaParaApi)
                } catch (e: Exception) {}
            }
        }
    }

    suspend fun registrarAsistencia(asistencia: Asistencia) {
        withContext(Dispatchers.IO) {
            asistenciaDao.insertarAsistencia(asistencia)
            try {
                apiService.guardarAsistencia(asistencia)
            } catch (e: Exception) {}
        }
    }

    suspend fun obtenerPorcentajeAsistencia(estudianteId: Int): Int {
        val total = detalleAsistenciaDao.totalClasesEstudiante(estudianteId)
        if (total == 0) return 0
        val presentes = detalleAsistenciaDao.totalAsistenciasEstudiante(estudianteId)
        return (presentes * 100) / total
    }
}
