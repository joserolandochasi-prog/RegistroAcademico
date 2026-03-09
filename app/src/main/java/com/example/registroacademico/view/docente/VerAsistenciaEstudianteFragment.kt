package com.example.registroacademico.view.docente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registroacademico.database.AppDatabase
import com.example.registroacademico.databinding.FragmentVerAsistenciaEstudianteBinding
import com.example.registroacademico.view.adapters.DetalleAsistenciaAdapter
import kotlinx.coroutines.launch

class VerAsistenciaEstudianteFragment : Fragment() {

    private var _binding: FragmentVerAsistenciaEstudianteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerAsistenciaEstudianteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val asistenciaId = arguments?.getInt("asistenciaId") ?: -1
        val fecha = arguments?.getString("fecha") ?: ""
        val hora = arguments?.getString("hora") ?: ""

        binding.tvFechaHoraDetalle.text = "Fecha: $fecha | Hora: $hora"

        binding.rvDetalleAsistencia.layoutManager = LinearLayoutManager(requireContext())

        val db = AppDatabase.getDatabase(requireContext())
        lifecycleScope.launch {
            val detalles = db.detalleAsistenciaDao().obtenerDetallesConNombre(asistenciaId)
            binding.rvDetalleAsistencia.adapter = DetalleAsistenciaAdapter(detalles)
        }

        binding.btnVolver.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}