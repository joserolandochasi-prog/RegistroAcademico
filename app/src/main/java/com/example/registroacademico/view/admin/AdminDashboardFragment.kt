package com.example.registroacademico.view.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registroacademico.R
import com.example.registroacademico.databinding.FragmentAdminDashboardBinding
import com.example.registroacademico.view.adapters.UsuarioAdapter
import com.example.registroacademico.viewmodel.UsuarioViewModel

class AdminDashboardFragment : Fragment() {

    private var _binding: FragmentAdminDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UsuarioViewModel by viewModels()
    private lateinit var adapter: UsuarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCerrarSesionAdmin.setOnClickListener {
            findNavController().navigate(R.id.action_adminDashboardFragment_to_loginFragment)
        }

        binding.btnGestionarMaterias.setOnClickListener {
            findNavController().navigate(R.id.action_adminDashboardFragment_to_gestionarMateriasFragment)
        }

        binding.btnGestionarEstudiantes.setOnClickListener {
            findNavController().navigate(R.id.action_adminDashboardFragment_to_gestionarEstudiantesFragment)
        }

        adapter = UsuarioAdapter(emptyList()) { usuario, nuevoRol ->
            if (nuevoRol == "docente") {
                viewModel.asignarRolDocente(usuario.id)
            } else {
                viewModel.asignarRolEstudiante(usuario)
            }
            Toast.makeText(requireContext(), "${usuario.nombre} ahora es $nuevoRol", Toast.LENGTH_SHORT).show()
        }

        binding.rvUsuarios.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsuarios.adapter = adapter

        viewModel.todosLosUsuarios.observe(viewLifecycleOwner) { usuarios ->
            adapter.actualizarLista(usuarios.filter { it.rol != "admin" })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
