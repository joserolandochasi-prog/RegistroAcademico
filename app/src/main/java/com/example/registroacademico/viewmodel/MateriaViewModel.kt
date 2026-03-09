package com.example.registroacademico.viewmodel

import androidx.lifecycle.*
import com.example.registroacademico.model.entities.Materia
import com.example.registroacademico.model.entities.Usuario
import com.example.registroacademico.repository.MateriaRepository
import com.example.registroacademico.repository.UsuarioRepository
import kotlinx.coroutines.launch

class MateriaViewModel(
    private val materiaRepository: MateriaRepository,
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    val todasLasMaterias: LiveData<List<Materia>> = materiaRepository.todasLasMaterias.asLiveData()

    fun insertarMateria(nombre: String, paralelo: String, docenteId: Int) {
        viewModelScope.launch {
            materiaRepository.insertar(Materia(nombre = nombre, paralelo = paralelo, docenteId = docenteId))
        }
    }

    suspend fun obtenerDocentes(): List<Usuario> {
        return usuarioRepository.obtenerDocentes()
    }
    
    fun obtenerMateriasPorDocente(docenteId: Int) = liveData {
        emit(materiaRepository.obtenerMateriasPorDocente(docenteId))
    }
}