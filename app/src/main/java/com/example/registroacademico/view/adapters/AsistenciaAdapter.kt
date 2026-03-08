package com.example.registroacademico.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.databinding.ItemEstudianteAsistenciaBinding
import com.example.registroacademico.model.entities.Estudiante

class AsistenciaAdapter(
    private var estudiantes: List<Estudiante> = emptyList()
) : RecyclerView.Adapter<AsistenciaAdapter.AsistenciaViewHolder>() {

    // 1. Solo necesitamos UN mapa para guardar los estados
    private val asistenciaMap = mutableMapOf<Int, Boolean>()

    inner class AsistenciaViewHolder(private val binding: ItemEstudianteAsistenciaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(estudiante: Estudiante) {
            binding.tvNombreEstudiante.text = estudiante.nombre

            // Evitamos errores de reciclaje quitando el listener antes de asignar el valor
            binding.cbAsistencia.setOnCheckedChangeListener(null)
            binding.cbAsistencia.isChecked = asistenciaMap[estudiante.id] ?: false

            // Cuando el profesor marca el check, guardamos el ID y el estado
            binding.cbAsistencia.setOnCheckedChangeListener { _, isChecked ->
                asistenciaMap[estudiante.id] = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsistenciaViewHolder {
        val binding = ItemEstudianteAsistenciaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AsistenciaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsistenciaViewHolder, position: Int) {
        holder.bind(estudiantes[position])
    }

    override fun getItemCount(): Int = estudiantes.size

    // 2. Función para actualizar la lista desde el ViewModel
    fun updateLista(nuevaLista: List<Estudiante>) {
        this.estudiantes = nuevaLista
        notifyDataSetChanged()
    }

    // 3. Esta es la función que llama el Fragmento para guardar en Room
    fun obtenerAsistencias(): Map<Int, Boolean> {
        return asistenciaMap
    }
}