package com.example.registroacademico.viewmodel

import androidx.lifecycle.*
import com.example.registroacademico.model.entities.Estudiante
import com.example.registroacademico.repository.EstudianteRepository
import kotlinx.coroutines.launch

class EstudianteViewModel(
    private val repository: EstudianteRepository
) : ViewModel() {

    private val _estudiantes = MutableLiveData<List<Estudiante>>()
    val estudiantes: LiveData<List<Estudiante>> = _estudiantes

    fun cargarEstudiantes() {
        viewModelScope.launch {
            _estudiantes.value = repository.obtenerEstudiantes()
        }
    }

    fun inscribirEstudiante(estudianteId: Int, materiaId: Int, nombreMateria: String, paralelo: String) {
        viewModelScope.launch {
            repository.inscribirEstudiante(estudianteId, materiaId, nombreMateria, paralelo)
            cargarEstudiantes()
        }
    }
    
    fun obtenerEstudiantesPorMateria(materiaId: Int) = liveData {
        emit(repository.obtenerEstudiantesPorMateria(materiaId))
    }
}
