package com.example.registroacademico.repository

import com.example.registroacademico.model.dao.AsistenciaDao
import com.example.registroacademico.model.entities.Asistencia
import java.util.Date

class AsistenciaRepository(private val asistenciaDao: AsistenciaDao) {

    suspend fun insertarAsistencias(asistenciasMarcadas: Map<Int, Boolean>) {
        val listaParaInsertar = asistenciasMarcadas.map { (idDelAlumno, estado) ->
            Asistencia(
                estudianteId = idDelAlumno,
                fecha = Date().toString(),
                presente = estado
            )
        }
        asistenciaDao.insertarListaAsistencia(listaParaInsertar)
    }
}