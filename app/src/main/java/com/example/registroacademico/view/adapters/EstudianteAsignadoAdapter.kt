package com.example.registroacademico.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.database.AppDatabase
import com.example.registroacademico.databinding.ItemEstudianteAsignadoBinding
import com.example.registroacademico.model.entities.Estudiante
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EstudianteAsignadoAdapter(
    private var students: List<Estudiante> = emptyList()
) : RecyclerView.Adapter<EstudianteAsignadoAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemEstudianteAsignadoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(student: Estudiante) {
            binding.tvNombreEstudiante.text = "${student.nombre} ${student.apellido}"
            
            val db = AppDatabase.getDatabase(binding.root.context)
            CoroutineScope(Dispatchers.IO).launch {
                val inscripciones = db.inscripcionDao().obtenerInscripcionesPorEstudiante(student.id)
                val todasLasMaterias = db.materiaDao().obtenerMaterias()
                
                val materiasDelEstudiante = todasLasMaterias.filter { m ->
                    inscripciones.any { it.materiaId == m.id }
                }
                
                val textoMaterias = if (materiasDelEstudiante.isEmpty()) {
                    "Sin materias asignadas"
                } else {
                    materiasDelEstudiante.joinToString("\n") { "${it.nombre} (${it.paralelo})" }
                }

                withContext(Dispatchers.Main) {
                    binding.tvClaseAsignada.text = textoMaterias
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEstudianteAsignadoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(students[position])
    }

    override fun getItemCount() = students.size

    fun updateLista(newList: List<Estudiante>) {
        students = newList
        notifyDataSetChanged()
    }
}
