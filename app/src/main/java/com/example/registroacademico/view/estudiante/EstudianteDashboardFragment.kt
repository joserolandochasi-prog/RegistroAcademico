package com.example.registroacademico.ui.estudiante

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.registroacademico.R

class EstudianteDashboardFragment : Fragment(R.layout.fragment_estudiante_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        findNavController().navigate(R.id.estudianteDashboardFragment)
        super.onViewCreated(view, savedInstanceState)

    }
}