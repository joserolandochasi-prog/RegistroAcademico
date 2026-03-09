package com.example.registroacademico.repository

import com.example.registroacademico.model.dao.EstudianteDao
import com.example.registroacademico.model.dao.InscripcionDao
import com.example.registroacademico.model.entities.Estudiante
import com.example.registroacademico.model.entities.Inscripcion

class EstudianteRepository(
    private val estudianteDao: EstudianteDao,
    private val inscripcionDao: InscripcionDao
) {

    suspend fun guardarEstudiante(estudiante: Estudiante) {
        estudianteDao.insertarEstudiante(estudiante)
    }

    suspend fun obtenerEstudiantes(): List<Estudiante> {
        return estudianteDao.obtenerEstudiantes()
    }

    suspend fun inscribirEstudiante(estudianteId: Int, materiaId: Int, nombreMateria: String, paralelo: String) {
        inscripcionDao.inscribir(Inscripcion(estudianteId = estudianteId, materiaId = materiaId))
        estudianteDao.asignarClase(estudianteId, nombreMateria, paralelo)
    }

    suspend fun obtenerEstudiantesPorMateria(materiaId: Int): List<Estudiante> {
        val inscripciones = inscripcionDao.obtenerInscripcionesPorMateria(materiaId)
        val todosLosEstudiantes = estudianteDao.obtenerEstudiantes()
        
        val idsInscritos = inscripciones.map { it.estudianteId }.toSet()
        return todosLosEstudiantes.filter { it.id in idsInscritos }
    }
}
