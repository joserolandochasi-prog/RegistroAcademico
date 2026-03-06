package com.example.registroacademico.view.docente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels // Esto es para el código 1
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registroacademico.databinding.FragmentTomarAsistenciaBinding
import com.example.registroacademico.view.adapters.AsistenciaAdapter
import com.example.registroacademico.viewmodel.AsistenciaViewModel

class TomarAsistenciaFragment : Fragment() {

    private var _binding: FragmentTomarAsistenciaBinding? = null
    // Dentro de la clase del Fragmento
    private val viewModel: AsistenciaViewModel by activityViewModels()
    private val binding get() = _binding!!

    // --- CÓDIGO 1: La variable del ViewModel ---


    private lateinit var asistenciaAdapter: AsistenciaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTomarAsistenciaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuramos el Adapter
        asistenciaAdapter = AsistenciaAdapter()
        binding.rvEstudiantes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = asistenciaAdapter
        }

        // --- CÓDIGO 2: El Observador ---
        // Esto hace que cuando la base de datos cambie, la lista se actualice
        viewModel.estudiantes.observe(viewLifecycleOwner) { lista ->
            asistenciaAdapter.updateLista(lista)
        }

        // --- CÓDIGO 3: El botón de Guardar ---
        binding.btnGuardarAsistencia.setOnClickListener {
            val asistencias = asistenciaAdapter.getAsistenciasMarcadas()
            viewModel.guardarAsistencia(asistencias)
            Toast.makeText(context, "Asistencia guardada con éxito", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}