package com.example.registroacademico.view.estudiante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.registroacademico.databinding.FragmentRegisterEstudianteBinding
import com.example.registroacademico.viewmodel.RegistroEstudianteViewModel


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterEstudianteBinding? = null
    private val binding get() = _binding!!


    private val viewModel: RegistroEstudianteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterEstudianteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGuardarEstudiante.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val apellido = binding.etApellido.text.toString().trim()

            if (nombre.isNotEmpty() && apellido.isNotEmpty()) {
                viewModel.registrar(nombre, apellido)
                Toast.makeText(requireContext(), "¡Estudiante $nombre guardado!", Toast.LENGTH_SHORT).show()

                binding.etNombre.text?.clear()
                binding.etApellido.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Por favor, completa los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
