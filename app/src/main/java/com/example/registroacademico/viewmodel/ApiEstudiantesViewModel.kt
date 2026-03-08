package com.example.registroacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroacademico.model.entities.Estudiante
import com.example.registroacademico.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApiEstudiantesViewModel : ViewModel() {

    private val _estudiantes = MutableStateFlow<List<Estudiante>>(emptyList())
    val estudiantes: StateFlow<List<Estudiante>> = _estudiantes

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarDesdeApi() {
        viewModelScope.launch {
            try {
                _estudiantes.value = RetrofitClient.api.obtenerEstudiantes()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al conectar con la API"
            }
        }
    }
}