package com.example.registroacademico.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.registroacademico.model.entities.Estudiante

class AsistenciaViewModel(application: Application) : AndroidViewModel(application) {

    private val _estudiantes = MutableLiveData<List<Estudiante>>()
    val estudiantes: LiveData<List<Estudiante>> get() = _estudiantes

    init {
        cargarEstudiantesDePrueba()
    }

    private fun cargarEstudiantesDePrueba() {
        val lista = listOf(
            Estudiante(1, "Maria", "Lopez", "maria@email.com", "123", "Estudiante"),
            Estudiante(2, "Juan", "Perez", "juan@email.com", "123", "Estudiante"),
            Estudiante(3, "Ana", "Gomez", "ana@email.com", "123", "Estudiante")
        )
        _estudiantes.value = lista
    }

    // ✅ AÑADE ESTA FUNCIÓN PARA QUITAR EL ERROR DEL BOTÓN
    fun guardarAsistencia(asistencias: Map<Int, Boolean>) {
        // Por ahora solo imprimimos en consola para que no falle
        println("Guardando asistencias: $asistencias")
        // Aquí luego conectaremos con el repositorio de Room
    }
}