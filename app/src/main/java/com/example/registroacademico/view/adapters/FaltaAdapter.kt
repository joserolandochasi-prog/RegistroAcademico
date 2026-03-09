package com.example.registroacademico.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.R
import com.example.registroacademico.model.entities.Asistencia

class FaltaAdapter : RecyclerView.Adapter<FaltaAdapter.FaltaViewHolder>() {

    private var listaFaltas: List<Asistencia> = emptyList()

    fun updateLista(nuevaLista: List<Asistencia>) {
        listaFaltas = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaltaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_falta, parent, false)
        return FaltaViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaltaViewHolder, position: Int) {
        val asistencia = listaFaltas[position]
        holder.tvFechaFalta.text = asistencia.fecha
        holder.tvHoraFalta.text = asistencia.hora
    }

    override fun getItemCount(): Int = listaFaltas.size

    class FaltaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFechaFalta: TextView = itemView.findViewById(R.id.tvFechaFalta)
        val tvHoraFalta: TextView = itemView.findViewById(R.id.tvHoraFalta)
    }
}
