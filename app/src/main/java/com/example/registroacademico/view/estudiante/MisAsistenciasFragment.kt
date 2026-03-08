package com.example.registroacademico.view.estudiante

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.registroacademico.R

class MisAsistenciasFragment : Fragment(R.layout.fragment_mis_asistencias) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTotal = view.findViewById<TextView>(R.id.tvTotalClases)
        val tvFaltas = view.findViewById<TextView>(R.id.tvFaltas)
        val tvPorcentaje = view.findViewById<TextView>(R.id.tvPorcentaje)

        val totalClases = 10
        val faltas = 2

        val porcentaje = ((totalClases - faltas).toFloat() / totalClases) * 100

        tvTotal.text = "Total clases: $totalClases"
        tvFaltas.text = "Faltas: $faltas"
        tvPorcentaje.text = "Asistencia: ${porcentaje.toInt()}%"
    }
}