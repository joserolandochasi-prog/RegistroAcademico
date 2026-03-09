package com.example.registroacademico.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.R
import com.example.registroacademico.model.entities.AsistenciaEstudianteInfo

class AsistenciaEstudianteAdapter : RecyclerView.Adapter<AsistenciaEstudianteAdapter.ViewHolder>() {

    private var lista: List<AsistenciaEstudianteInfo> = emptyList()

    fun updateLista(nuevaLista: List<AsistenciaEstudianteInfo>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asistencia_estudiante, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.tvMateria.text = item.nombreMateria
        holder.tvFechaHora.text = "${item.fecha} - ${item.hora}"
        
        if (item.presente) {
            holder.tvStatus.text = "PRESENTE"
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_dark))
            holder.ivIcon.setImageResource(android.R.drawable.ic_menu_today)
            holder.ivIcon.setColorFilter(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_dark))
        } else {
            holder.tvStatus.text = "FALTA"
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_dark))
            holder.ivIcon.setImageResource(android.R.drawable.ic_menu_today)
            holder.ivIcon.setColorFilter(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_dark))
        }
    }

    override fun getItemCount(): Int = lista.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMateria: TextView = view.findViewById(R.id.tvMateriaNombre)
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
        val tvStatus: TextView = view.findViewById(R.id.tvStatusText)
        val ivIcon: ImageView = view.findViewById(R.id.ivStatusIcon)
    }
}
