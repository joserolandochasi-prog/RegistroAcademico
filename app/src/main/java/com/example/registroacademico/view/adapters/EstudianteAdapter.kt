package com.example.registroacademico.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.R
import com.example.registroacademico.model.entities.Estudiante

class EstudianteAdapter(
    private var estudiantes: List<Estudiante>
) : RecyclerView.Adapter<EstudianteAdapter.ViewHolder>() {

    private var asistenciaMap = estudiantes.associate { it.id to false }.toMutableMap()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val checkAsistencia: CheckBox = view.findViewById(R.id.checkAsistencia)
    }

    fun actualizarLista(nuevaLista: List<Estudiante>) {
        estudiantes = nuevaLista
        asistenciaMap = estudiantes.associate { it.id to false }.toMutableMap()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estudiante, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val estudiante = estudiantes[position]
        holder.tvNombre.text = "${estudiante.nombre} ${estudiante.apellido}"

        holder.checkAsistencia.setOnCheckedChangeListener(null)
        holder.checkAsistencia.isChecked = asistenciaMap[estudiante.id] ?: false

        holder.checkAsistencia.setOnCheckedChangeListener { _, checked ->
            asistenciaMap[estudiante.id] = checked
        }
    }

    override fun getItemCount(): Int = estudiantes.size

    fun obtenerAsistencias(): Map<Int, Boolean> {
        return asistenciaMap
    }
}
