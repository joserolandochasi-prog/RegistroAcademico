package com.example.registroacademico.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.databinding.ItemRegistroAsistenciaBinding
import com.example.registroacademico.model.entities.AsistenciaConResumen

class AsistenciaRecienteAdapter(
    private var asistencias: List<AsistenciaConResumen> = emptyList(),
    private val onItemClick: (AsistenciaConResumen) -> Unit
) : RecyclerView.Adapter<AsistenciaRecienteAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRegistroAsistenciaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(data: AsistenciaConResumen) {
            // Mostrar el nombre de la materia en el registro
            binding.tvMateriaAsistencia.text = if (data.asistencia.nombreMateria.isNotEmpty()) {
                data.asistencia.nombreMateria
            } else {
                "Materia General"
            }
            
            binding.tvFechaAsistencia.text = data.asistencia.fecha
            binding.tvHoraAsistencia.text = data.asistencia.hora
            binding.tvPorcentajeDia.text = data.resumenAsistencia
            
            binding.root.setOnClickListener {
                onItemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRegistroAsistenciaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asistencias[position])
    }

    override fun getItemCount(): Int = asistencias.size

    fun updateLista(nuevaLista: List<AsistenciaConResumen>) {
        this.asistencias = nuevaLista
        notifyDataSetChanged()
    }
}
