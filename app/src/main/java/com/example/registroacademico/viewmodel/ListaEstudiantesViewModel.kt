package com.example.registroacademico.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroacademico.database.AppDatabase
import com.example.registroacademico.model.entities.Estudiante
import com.example.registroacademico.repository.EstudianteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListaEstudiantesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EstudianteRepository

    private val _estudiantes = MutableStateFlow<List<Estudiante>>(emptyList())
    val estudiantes: StateFlow<List<Estudiante>> = _estudiantes

    init {
        val dao = AppDatabase.getDatabase(application).estudianteDao()
        repository = EstudianteRepository(dao)
        cargarEstudiantes()
    }

    fun cargarEstudiantes() {
        viewModelScope.launch {
            _estudiantes.value = repository.obtenerEstudiantes()
        }
    }

    fun eliminar(estudiante: Estudiante) {
        viewModelScope.launch {
            repository.eliminarEstudiante(estudiante)
            cargarEstudiantes()
        }
    }
}