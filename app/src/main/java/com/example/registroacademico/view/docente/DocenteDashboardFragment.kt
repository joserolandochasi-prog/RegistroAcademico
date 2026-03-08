package com.example.registroacademico.ui.docente

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.registroacademico.R

class DocenteDashboardFragment : Fragment(R.layout.fragment_docente_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val btnTomar = view.findViewById<Button>(R.id.btnTomarAsistencia)
        val btnEstadisticas = view.findViewById<Button>(R.id.btnEstadisticas)

        btnTomar.setOnClickListener {
            findNavController().navigate(R.id.tomarAsistenciaFragment)
        }

        btnEstadisticas.setOnClickListener {
            findNavController().navigate(R.id.estadisticasFragment)
        }

    }
}