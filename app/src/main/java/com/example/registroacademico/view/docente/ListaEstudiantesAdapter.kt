package com.example.registroacademico.view.docente

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.databinding.ItemEstudianteListaBinding
import com.example.registroacademico.model.entities.Estudiante

class ListaEstudiantesAdapter(
    private var lista: List<Estudiante>,
    private val onEliminarClick: (Estudiante) -> Unit
) : RecyclerView.Adapter<ListaEstudiantesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemEstudianteListaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEstudianteListaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val estudiante = lista[position]
        holder.binding.tvNombreCompleto.text = "${estudiante.nombre} ${estudiante.apellido}"

        holder.binding.btnEliminar.setOnClickListener {
            onEliminarClick(estudiante)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<Estudiante>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}