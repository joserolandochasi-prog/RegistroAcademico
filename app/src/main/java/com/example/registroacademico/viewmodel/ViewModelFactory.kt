package com.example.registroacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registroacademico.repository.AsistenciaRepository
import com.example.registroacademico.repository.EstudianteRepository

class ViewModelFactory(
    private val repository: Any
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(EstudianteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EstudianteViewModel(repository as EstudianteRepository) as T
        }
        
        if (modelClass.isAssignableFrom(AsistenciaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AsistenciaViewModel(repository as AsistenciaRepository) as T
        }

        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}