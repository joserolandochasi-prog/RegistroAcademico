package com.example.registroacademico.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.databinding.ItemMateriaBinding
import com.example.registroacademico.model.entities.Materia

class MateriaAdapter(
    private var materias: List<Materia> = emptyList(),
    private val onEliminarClick: (Materia) -> Unit
) : RecyclerView.Adapter<MateriaAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMateriaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(materia: Materia) {
            binding.tvNombreMateria.text = materia.nombre
            binding.tvParaleloMateria.text = "Paralelo: ${materia.paralelo}"
            binding.btnEliminar.setOnClickListener { onEliminarClick(materia) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMateriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(materias[position])
    }

    override fun getItemCount() = materias.size

    fun updateLista(nuevaLista: List<Materia>) {
        materias = nuevaLista
        notifyDataSetChanged()
    }
}