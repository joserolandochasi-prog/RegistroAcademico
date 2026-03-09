package com.example.registroacademico.repository

import androidx.lifecycle.LiveData
import com.example.registroacademico.model.dao.UsuarioDao
import com.example.registroacademico.model.entities.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    val todosLosUsuarios: LiveData<List<Usuario>> = usuarioDao.obtenerTodos()

    suspend fun insertar(usuario: Usuario) {
        usuarioDao.insertar(usuario)
    }

    fun login(email: String, password: String): LiveData<Usuario?> {
        return usuarioDao.login(email, password)
    }

    suspend fun hacerDocente(usuarioId: Int) {
        usuarioDao.actualizarRol(usuarioId, "docente")
    }

    suspend fun obtenerDocentes(): List<Usuario> {
        return usuarioDao.obtenerDocentes()
    }
}