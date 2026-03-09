package com.example.registroacademico.repository

import com.example.registroacademico.model.dao.MateriaDao
import com.example.registroacademico.model.entities.Materia
import kotlinx.coroutines.flow.Flow

class MateriaRepository(private val materiaDao: MateriaDao) {
    val todasLasMaterias: Flow<List<Materia>> = materiaDao.obtenerMateriasFlow()

    suspend fun insertar(materia: Materia) {
        materiaDao.insertarMateria(materia)
    }

    suspend fun eliminar(materia: Materia) {
        materiaDao.eliminarMateria(materia)
    }

    suspend fun obtenerMateriasPorDocente(docenteId: Int): List<Materia> {
        return materiaDao.obtenerMateriasPorDocente(docenteId)
    }
}