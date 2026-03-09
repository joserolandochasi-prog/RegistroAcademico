package com.example.registroacademico.view.estudiante

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.R
import com.example.registroacademico.database.AppDatabase
import com.example.registroacademico.network.RetrofitClient
import com.example.registroacademico.repository.AsistenciaRepository
import com.example.registroacademico.utils.SessionManager
import com.example.registroacademico.view.adapters.AsistenciaEstudianteAdapter
import com.example.registroacademico.viewmodel.AsistenciaViewModel
import com.example.registroacademico.viewmodel.ViewModelFactory

class EstudianteDashboardFragment : Fragment(R.layout.fragment_estudiante_dashboard) {

    private lateinit var viewModel: AsistenciaViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: AsistenciaEstudianteAdapter
    private lateinit var tvPorcentaje: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        val studentId = sessionManager.getUserId()

        setupViewModel()

        tvPorcentaje = view.findViewById(R.id.tvPorcentajeAsistencia)
        val rvHistorial = view.findViewById<RecyclerView>(R.id.rvFaltas) // Reusando el RV existente para el historial completo
        val tvTituloLista = view.findViewById<TextView>(R.id.tvTituloFaltas) ?: view.findViewById(R.id.tvPorcentajeAsistencia) // Referencia de seguridad


        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesionEstudiante)

        adapter = AsistenciaEstudianteAdapter()
        rvHistorial.layoutManager = LinearLayoutManager(requireContext())
        rvHistorial.adapter = adapter

        btnCerrarSesion.setOnClickListener {
            sessionManager.clearSession()
            findNavController().navigate(R.id.action_estudianteDashboardFragment_to_loginFragment)
        }

        if (studentId != -1) {
            viewModel.obtenerHistorialEstudiante(studentId).observe(viewLifecycleOwner) { historial ->
                adapter.updateLista(historial)
            }

            viewModel.porcentajeAsistencia.observe(viewLifecycleOwner) { porcentaje ->
                tvPorcentaje.text = "$porcentaje%"
            }

            viewModel.cargarPorcentajeAsistencia(studentId)
        }
    }

    private fun setupViewModel() {
        val db = AppDatabase.getDatabase(requireContext())
        val repository = AsistenciaRepository(
            RetrofitClient.apiService,
            db.asistenciaDao(),
            db.detalleAsistenciaDao()
        )
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[AsistenciaViewModel::class.java]
    }
}
