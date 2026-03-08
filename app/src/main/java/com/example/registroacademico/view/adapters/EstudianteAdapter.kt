package com.example.registroacademico.ui.docente

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.databinding.ItemEstudianteBinding
import com.example.registroacademico.model.entities.Estudiante

class EstudianteAdapter(
    private var estudiantes: List<Estudiante>
) : RecyclerView.Adapter<EstudianteAdapter.ViewHolder>() {

    private val asistenciaMap = mutableMapOf<Int, Boolean>()

    inner class ViewHolder(val binding: ItemEstudianteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEstudianteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val estudiante = estudiantes[position]

        holder.binding.tvNombre.text = estudiante.nombre

        holder.binding.checkAsistencia.setOnCheckedChangeListener(null)
        holder.binding.checkAsistencia.isChecked = asistenciaMap[estudiante.id] ?: false

        holder.binding.checkAsistencia.setOnCheckedChangeListener { _, checked ->
            asistenciaMap[estudiante.id] = checked
        }
    }

    override fun getItemCount(): Int = estudiantes.size

    // Actualizar lista
    fun actualizarLista(nuevaLista: List<Estudiante>) {
        estudiantes = nuevaLista
        notifyDataSetChanged()
    }

    // Obtener asistencia marcada
    fun obtenerAsistencias(): Map<Int, Boolean> {
        return asistenciaMap
    }
}