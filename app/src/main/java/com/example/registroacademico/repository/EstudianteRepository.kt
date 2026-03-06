package com.example.registroacademico.repository

import com.example.registroacademico.model.dao.EstudianteDao
import com.example.registroacademico.model.entities.Estudiante

class EstudianteRepository(private val estudianteDao: EstudianteDao) {

    suspend fun getAllEstudiantes(): List<Estudiante> {
        return estudianteDao.getAllEstudiantes()
    }
}