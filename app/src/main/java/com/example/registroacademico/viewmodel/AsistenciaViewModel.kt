package com.example.registroacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroacademico.model.entities.Asistencia
import com.example.registroacademico.repository.AsistenciaRepository
import kotlinx.coroutines.launch

class AsistenciaViewModel(
    private val repository: AsistenciaRepository
) : ViewModel() {

    fun guardar(asistencia: Asistencia) {
        viewModelScope.launch {
            repository.guardar(asistencia)
        }
    }
}