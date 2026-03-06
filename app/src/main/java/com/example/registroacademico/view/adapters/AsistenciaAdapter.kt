package com.example.registroacademico.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.databinding.ItemEstudianteAsistenciaBinding
import com.example.registroacademico.model.entities.Estudiante

class AsistenciaAdapter(
    private var estudiantes: List<Estudiante> = emptyList()
) : RecyclerView.Adapter<AsistenciaAdapter.AsistenciaViewHolder>() {

    // Aquí guardaremos quiénes marcaron asistencia (ID del estudiante -> Presente/Ausente)
    private val asistenciaMap = mutableMapOf<Int, Boolean>()

    inner class AsistenciaViewHolder(private val binding: ItemEstudianteAsistenciaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(estudiante: Estudiante) {
            binding.tvNombreEstudiante.text = "${estudiante.nombre} ${estudiante.apellido}"

            // Configurar el checkbox según nuestro mapa
            binding.cbAsistencia.isChecked = asistenciaMap[estudiante.id] ?: false

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

    fun updateLista(nuevaLista: List<Estudiante>) {
        this.estudiantes = nuevaLista
        notifyDataSetChanged()
    }

    fun getAsistenciasMarcadas(): Map<Int, Boolean> = asistenciaMap
}