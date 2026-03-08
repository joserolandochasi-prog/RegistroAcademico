package com.example.registroacademico.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.registroacademico.model.entities.Docente

@Dao
interface DocenteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarDocente(docente: Docente)

    @Query("SELECT * FROM docentes")
    suspend fun obtenerDocentes(): List<Docente>

    @Query("SELECT * FROM docentes WHERE id = :id LIMIT 1")
    suspend fun obtenerDocentePorId(id: Int): Docente?

    @Query("DELETE FROM docentes WHERE id = :id")
    suspend fun eliminarDocente(id: Int)
}