package com.example.registroacademico.model.dao

import androidx.room.*
import com.example.registroacademico.model.entities.Materia
import kotlinx.coroutines.flow.Flow

@Dao
interface MateriaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarMateria(materia: Materia)

    @Query("SELECT * FROM materias")
    fun obtenerMateriasFlow(): Flow<List<Materia>>

    @Query("SELECT * FROM materias")
    suspend fun obtenerMaterias(): List<Materia>

    @Query("SELECT * FROM materias WHERE docenteId = :docenteId")
    suspend fun obtenerMateriasPorDocente(docenteId: Int): List<Materia>

    @Delete
    suspend fun eliminarMateria(materia: Materia)
}
