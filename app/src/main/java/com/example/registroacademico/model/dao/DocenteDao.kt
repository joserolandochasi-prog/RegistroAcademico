package com.example.registroacademico.model.dao

import androidx.room.*
import com.example.registroacademico.model.entities.Docente

@Dao
interface DocenteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarDocente(docente: Docente)

    @Query("SELECT * FROM docentes")
    suspend fun obtenerDocentes(): List<Docente>

    @Query("SELECT * FROM docentes WHERE email = :email AND contrasena = :password LIMIT 1")
    suspend fun login(email: String, password: String): Docente?

    @Delete
    suspend fun eliminarDocente(docente: Docente)
}