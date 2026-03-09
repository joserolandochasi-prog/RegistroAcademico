package com.example.registroacademico.view.docente

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.R
import com.example.registroacademico.database.AppDatabase
import com.example.registroacademico.network.RetrofitClient
import com.example.registroacademico.repository.AsistenciaRepository
import com.example.registroacademico.view.adapters.AsistenciaRecienteAdapter
import com.example.registroacademico.viewmodel.AsistenciaViewModel
import com.example.registroacademico.viewmodel.ViewModelFactory

class DocenteDashboardFragment : Fragment(R.layout.fragment_docente_dashboard) {

    private lateinit var viewModel: AsistenciaViewModel
    private lateinit var adapter: AsistenciaRecienteAdapter
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        val btnTomar = view.findViewById<Button>(R.id.btnTomarAsistencia)
        val btnEstadisticas = view.findViewById<Button>(R.id.btnEstadisticas)
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)
        val rvRegistros = view.findViewById<RecyclerView>(R.id.rvRegistrosAsistencia)
        progressBar = view.findViewById(R.id.progressBarDashboard)

        adapter = AsistenciaRecienteAdapter { item ->
            val bundle = Bundle().apply {
                putInt("asistenciaId", item.asistencia.id)
                putString("fecha", item.asistencia.fecha)
                putString("hora", item.asistencia.hora)
            }
            findNavController().navigate(
                R.id.action_docenteDashboardFragment_to_verAsistenciaEstudianteFragment, 
                bundle
            )
        }
        
        rvRegistros.layoutManager = LinearLayoutManager(requireContext())
        rvRegistros.adapter = adapter

        btnTomar.setOnClickListener {
            findNavController().navigate(R.id.tomarAsistenciaFragment)
        }

        btnEstadisticas.setOnClickListener {
            findNavController().navigate(R.id.estadisticasFragment)
        }

        btnCerrarSesion.setOnClickListener {
            findNavController().navigate(R.id.action_docenteDashboardFragment_to_loginFragment)
        }

        observeViewModel()
        

        viewModel.refrescarAsistencias()
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

    private fun observeViewModel() {
        viewModel.listaAsistenciasConResumen.observe(viewLifecycleOwner) { lista ->
            adapter.updateLista(lista)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            progressBar.isVisible = loading
        }
    }
}