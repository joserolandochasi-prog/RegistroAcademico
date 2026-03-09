package com.example.registroacademico.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.R
import com.example.registroacademico.databinding.ItemEstudianteDetalleBinding
import com.example.registroacademico.model.entities.DetalleConNombre

class DetalleAsistenciaAdapter(
    private val lista: List<DetalleConNombre>
) : RecyclerView.Adapter<DetalleAsistenciaAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemEstudianteDetalleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DetalleConNombre) {
            binding.tvNombreEstudiante.text = "${item.nombre} ${item.apellido}"
            
            if (item.presente) {
                binding.ivEstadoAsistencia.setImageResource(android.R.drawable.presence_online)
                binding.ivEstadoAsistencia.setColorFilter(
                    ContextCompat.getColor(binding.root.context, android.R.color.holo_green_dark)
                )
            } else {
                binding.ivEstadoAsistencia.setImageResource(android.R.drawable.presence_busy)
                binding.ivEstadoAsistencia.setColorFilter(
                    ContextCompat.getColor(binding.root.context, android.R.color.holo_red_dark)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEstudianteDetalleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount(): Int = lista.size
}