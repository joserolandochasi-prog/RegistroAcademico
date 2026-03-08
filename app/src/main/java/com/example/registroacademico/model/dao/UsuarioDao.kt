package com.example.registroacademico.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.registroacademico.model.entities.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    fun login(email: String, password: String): LiveData<Usuario?>
}