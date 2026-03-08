package com.example.registroacademico.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroacademico.database.AppDatabase
import com.example.registroacademico.model.entities.Estudiante
import com.example.registroacademico.repository.EstudianteRepository
import kotlinx.coroutines.launch

class RegistroEstudianteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EstudianteRepository

    init {
        val dao = AppDatabase.getDatabase(application).estudianteDao()
        repository = EstudianteRepository(dao)
    }

    fun registrar(nombre: String, apellido: String) {
        viewModelScope.launch {
            val nuevoEstudiante = Estudiante(
                id = "",
                nombre = nombre,
                apellido = apellido
            )
            repository.guardarEstudiante(nuevoEstudiante)
        }
    }
}