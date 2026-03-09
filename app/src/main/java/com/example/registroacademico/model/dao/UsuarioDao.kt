package com.example.registroacademico.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.registroacademico.model.entities.Usuario

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    fun login(email: String, password: String): LiveData<Usuario?>

    @Query("SELECT * FROM usuarios")
    fun obtenerTodos(): LiveData<List<Usuario>>

    @Query("SELECT * FROM usuarios WHERE rol = 'docente'")
    suspend fun obtenerDocentes(): List<Usuario>

    @Query("UPDATE usuarios SET rol = :nuevoRol WHERE id = :usuarioId")
    suspend fun actualizarRol(usuarioId: Int, nuevoRol: String)

    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun contarUsuarios(): Int
}