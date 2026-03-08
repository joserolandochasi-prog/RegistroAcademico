package com.example.registroacademico.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroacademico.database.AppDatabase
import com.example.registroacademico.model.entities.Asistencia
import com.example.registroacademico.model.entities.DetalleAsistencia
import com.example.registroacademico.repository.AsistenciaRepository
import kotlinx.coroutines.launch

class AsistenciaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AsistenciaRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = AsistenciaRepository(
            db.asistenciaDao(),
            db.detalleAsistenciaDao()
        )
    }

    fun guardarAsistenciaConDetalles(
        asistencia: Asistencia,
        detalles: List<DetalleAsistencia>,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            repository.guardarAsistenciaConDetalles(asistencia, detalles)
            onSuccess()
        }
    }
    fun obtenerEstadisticas(
        estudianteId: Int,
        onResult: (total: Int, faltas: Int) -> Unit
    ) {
        viewModelScope.launch {

            val total = repository.obtenerTotalPorEstudiante(estudianteId)
            val faltas = repository.obtenerFaltasPorEstudiante(estudianteId)

            onResult(total, faltas)
        }
    }
}