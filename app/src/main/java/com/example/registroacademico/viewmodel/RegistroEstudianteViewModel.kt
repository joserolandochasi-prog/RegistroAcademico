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
        val database = AppDatabase.getDatabase(application)
        val estudianteDao = database.estudianteDao()
        val inscripcionDao = database.inscripcionDao()
        repository = EstudianteRepository(estudianteDao, inscripcionDao)
    }

    fun registrar(nombre: String, apellido: String) {
        viewModelScope.launch {
            val nuevoEstudiante = Estudiante(
                nombre = nombre,
                apellido = apellido
            )
            repository.guardarEstudiante(nuevoEstudiante)
        }
    }
}
 