package com.example.registroacademico.viewmodel

import androidx.lifecycle.*
import com.example.registroacademico.model.entities.Asistencia
import com.example.registroacademico.model.entities.AsistenciaConResumen
import com.example.registroacademico.model.entities.DetalleAsistencia
import com.example.registroacademico.model.entities.AsistenciaEstudianteInfo
import com.example.registroacademico.repository.AsistenciaRepository
import kotlinx.coroutines.launch

class AsistenciaViewModel(
    private val repository: AsistenciaRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _guardadoExitoso = MutableLiveData<Boolean>()
    val guardadoExitoso: LiveData<Boolean> get() = _guardadoExitoso

    private val _porcentajeAsistencia = MutableLiveData<Int>()
    val porcentajeAsistencia: LiveData<Int> get() = _porcentajeAsistencia

    val listaAsistenciasConResumen: LiveData<List<AsistenciaConResumen>> = 
        repository.asistenciasConResumen.asLiveData()

    val listaAsistencias: LiveData<List<Asistencia>> = repository.todasLasAsistencias.asLiveData()

    fun obtenerFaltasEstudiante(estudianteId: Int): LiveData<List<Asistencia>> {
        return repository.obtenerFaltasEstudiante(estudianteId).asLiveData()
    }

    fun obtenerHistorialEstudiante(estudianteId: Int): LiveData<List<AsistenciaEstudianteInfo>> {
        return repository.obtenerHistorialEstudiante(estudianteId).asLiveData()
    }

    fun cargarPorcentajeAsistencia(estudianteId: Int) {
        viewModelScope.launch {
            _porcentajeAsistencia.value = repository.obtenerPorcentajeAsistencia(estudianteId)
        }
    }

    fun refrescarAsistencias() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.refrescarAsistencias()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun guardarAsistenciaCompleta(asistencia: Asistencia, detalles: List<DetalleAsistencia>) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.registrarAsistenciaCompleta(asistencia, detalles)
                _guardadoExitoso.value = true
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al guardar asistencia: ${e.message}"
                _guardadoExitoso.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun guardarAsistencia(asistencia: Asistencia) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.registrarAsistencia(asistencia)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al guardar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
