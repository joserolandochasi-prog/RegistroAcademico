package com.example.registroacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroacademico.model.entities.Estudiante
import com.example.registroacademico.repository.EstudianteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EstudianteViewModel(
    private val repository: EstudianteRepository
) : ViewModel() {

    private val _estudiantes = MutableStateFlow<List<Estudiante>>(emptyList())
    val estudiantes: StateFlow<List<Estudiante>> = _estudiantes

    fun cargarEstudiantes() {
        viewModelScope.launch {
            val lista = repository.obtenerEstudiantes()
            _estudiantes.value = lista
        }
    }
}