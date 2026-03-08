package com.example.registroacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registroacademico.repository.EstudianteRepository

class ViewModelFactory(
    private val repository: EstudianteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(EstudianteViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return EstudianteViewModel(repository) as T

        }

        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}