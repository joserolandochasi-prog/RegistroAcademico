package com.example.registroacademico.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.registroacademico.database.AppDatabase
import com.example.registroacademico.model.entities.Estudiante
import com.example.registroacademico.model.entities.Usuario
import com.example.registroacademico.repository.UsuarioRepository
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UsuarioRepository
    val todosLosUsuarios: LiveData<List<Usuario>>

    init {
        val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
        repository = UsuarioRepository(usuarioDao)
        todosLosUsuarios = repository.todosLosUsuarios
    }

    fun registrar(nombre: String, email: String, password: String) = viewModelScope.launch {
        val nuevoUsuario = Usuario(nombre = nombre, email = email, password = password, rol = "pendiente")
        repository.insertar(nuevoUsuario)
    }

    fun login(email: String, password: String): LiveData<Usuario?> {
        return repository.login(email, password)
    }

    fun asignarRolDocente(usuarioId: Int) = viewModelScope.launch {
        repository.hacerDocente(usuarioId)
    }
    
    fun asignarRolEstudiante(usuario: Usuario) = viewModelScope.launch {
        val db = AppDatabase.getDatabase(getApplication())
        db.usuarioDao().actualizarRol(usuario.id, "estudiante")
        
        val nuevoEstudiante = Estudiante(
            id = usuario.id,
            nombre = usuario.nombre,
            apellido = "",
            email = usuario.email
        )
        db.estudianteDao().insertarEstudiante(nuevoEstudiante)
    }
}