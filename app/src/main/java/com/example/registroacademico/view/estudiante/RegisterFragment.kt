package com.example.registroacademico.view.estudiante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.registroacademico.databinding.FragmentRegisterBinding // Este conecta con tu XML
import com.example.registroacademico.viewmodel.RegistroEstudianteViewModel


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    // Conecta con el ViewModel que gestiona Room
    private val viewModel: RegistroEstudianteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflamos el diseño que ya tienes en layout
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuramos el botón de tu fragment_register.xml
        binding.btnGuardarEstudiante.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val apellido = binding.etApellido.text.toString().trim()

            if (nombre.isNotEmpty() && apellido.isNotEmpty()) {
                viewModel.registrar(nombre, apellido)
                Toast.makeText(requireContext(), "¡Estudiante $nombre guardado!", Toast.LENGTH_SHORT).show()

                // Limpiamos los campos
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