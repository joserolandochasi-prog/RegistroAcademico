package com.example.registroacademico.repository

import com.example.registroacademico.model.dao.UsuarioDao

class AuthRepository(private val usuarioDao: UsuarioDao) {

    fun login(email: String, password: String) =
        usuarioDao.login(email, password)
}