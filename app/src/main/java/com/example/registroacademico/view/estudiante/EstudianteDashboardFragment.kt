package com.example.registroacademico.view.estudiante

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.registroacademico.R
import com.example.registroacademico.viewmodel.AsistenciaViewModel

class EstudianteDashboardFragment : Fragment(R.layout.fragment_estudiante_dashboard) {

    private val viewModel: AsistenciaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtTotal = view.findViewById<TextView>(R.id.txtTotalClases)
        val txtFaltas = view.findViewById<TextView>(R.id.txtFaltas)
        val txtPorcentaje = view.findViewById<TextView>(R.id.txtPorcentaje)

        val estudianteId = 1 // temporal para demo

        viewModel.obtenerEstadisticas(estudianteId) { total, faltas ->

            val porcentaje = if (total > 0) {
                ((total - faltas) * 100) / total
            } else 0

            txtTotal.text = "Total clases: $total"
            txtFaltas.text = "Faltas: $faltas"
            txtPorcentaje.text = "Asistencia: $porcentaje%"
        }
    }
}