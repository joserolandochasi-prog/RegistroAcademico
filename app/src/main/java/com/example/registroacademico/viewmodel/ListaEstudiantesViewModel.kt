package com.example.registroacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroacademico.model.entities.Estudiante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListaEstudiantesViewModel : ViewModel() {

    private val _estudiantes = MutableStateFlow<List<Estudiante>>(emptyList())
    val estudiantes: StateFlow<List<Estudiante>> = _estudiantes

    init {
        cargarEstudiantes()
    }

    fun cargarEstudiantes() {
        viewModelScope.launch {
            _estudiantes.value = listOf(
                Estudiante(id = "1", nombre = "Juan", apellido = "Pérez"),
                Estudiante(id = "2", nombre = "María", apellido = "López"),
                Estudiante(id = "3", nombre = "Carlos", apellido = "Sánchez"),
                Estudiante(id = "4", nombre = "Ana", apellido = "Torres")
            )
        }
    }

    fun eliminar(estudiante: Estudiante) {
        viewModelScope.launch {
            _estudiantes.value = _estudiantes.value.filter { it.id != estudiante.id }
        }
    }
}