package com.example.registroacademico.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.registroacademico.model.entities.Usuario

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): Usuario?

    @Query("SELECT * FROM usuarios")
    suspend fun obtenerUsuarios(): List<Usuario>

    @Query("DELETE FROM usuarios")
    suspend fun eliminarTodos()
}