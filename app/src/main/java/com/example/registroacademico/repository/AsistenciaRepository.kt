package com.example.registroacademico.repository

import com.example.registroacademico.model.dao.AsistenciaDao
import com.example.registroacademico.model.dao.DetalleAsistenciaDao
import com.example.registroacademico.model.entities.Asistencia
import com.example.registroacademico.model.entities.AsistenciaConResumen
import com.example.registroacademico.model.entities.AsistenciaEstudianteInfo
import com.example.registroacademico.model.entities.DetalleAsistencia
import com.example.registroacademico.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AsistenciaRepository(
    private val apiService: ApiService,
    private val asistenciaDao: AsistenciaDao,
    private val detalleAsistenciaDao: DetalleAsistenciaDao
) {

    val asistenciasConResumen: Flow<List<AsistenciaConResumen>> =
        asistenciaDao.obtenerAsistenciasConResumen()

    val todasLasAsistencias: Flow<List<Asistencia>> =
        asistenciaDao.obtenerAsistenciasFlow()

    suspend fun registrarAsistenciaCompleta(asistencia: Asistencia, detalles: List<DetalleAsistencia>) {
        val idAsistencia = asistenciaDao.insertarAsistencia(asistencia)
        val detallesConId = detalles.map { it.copy(asistenciaId = idAsistencia.toInt()) }
        detalleAsistenciaDao.insertarLista(detallesConId)
    }

    suspend fun registrarAsistencia(asistencia: Asistencia) {
        asistenciaDao.insertarAsistencia(asistencia)
    }

    suspend fun refrescarAsistencias() {
        // Implementación para sincronizar con API si es necesario
    }

    fun obtenerHistorialEstudiante(estudianteId: Int): Flow<List<AsistenciaEstudianteInfo>> {
        return detalleAsistenciaDao.obtenerPorEstudiante(estudianteId).map { detalles ->
            val asistencias = asistenciaDao.obtenerAsistencias()
            detalles.map { detalle ->
                val asistencia = asistencias.find { it.id == detalle.asistenciaId }
                AsistenciaEstudianteInfo(
                    fecha = asistencia?.fecha ?: "",
                    hora = asistencia?.hora ?: "",
                    nombreMateria = asistencia?.nombreMateria ?: "",
                    presente = detalle.presente
                )
            }
        }
    }

    fun obtenerFaltasEstudiante(estudianteId: Int): Flow<List<Asistencia>> {
        return detalleAsistenciaDao.obtenerPorEstudiante(estudianteId).map { detalles ->
            val faltasIds = detalles.filter { !it.presente }.map { it.asistenciaId }
            asistenciaDao.obtenerAsistencias().filter { it.id in faltasIds }
        }
    }

    suspend fun obtenerPorcentajeAsistencia(estudianteId: Int): Int {
        val total = detalleAsistenciaDao.totalClasesEstudiante(estudianteId)
        if (total == 0) return 0
        val asistidas = detalleAsistenciaDao.totalAsistenciasEstudiante(estudianteId)
        return (asistidas * 100) / total
    }
}
