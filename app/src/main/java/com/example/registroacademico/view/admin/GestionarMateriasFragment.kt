package com.example.registroacademico.view.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registroacademico.database.AppDatabase
import com.example.registroacademico.databinding.FragmentGestionarMateriasBinding
import com.example.registroacademico.model.entities.Usuario
import com.example.registroacademico.repository.MateriaRepository
import com.example.registroacademico.repository.UsuarioRepository
import com.example.registroacademico.view.adapters.MateriaAdapter
import com.example.registroacademico.viewmodel.MateriaViewModel
import kotlinx.coroutines.launch

class GestionarMateriasFragment : Fragment() {

    private var _binding: FragmentGestionarMateriasBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MateriaViewModel
    private lateinit var adapter: MateriaAdapter
    private var listaDocentes: List<Usuario> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGestionarMateriasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        cargarDocentes()

        binding.btnAtrasMaterias.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnCrearMateria.setOnClickListener {
            val nombre = binding.etNombreMateria.text.toString()
            val paralelo = binding.etParalelo.text.toString()
            val docentePos = binding.spinnerDocentes.selectedItemPosition

            if (nombre.isEmpty() || paralelo.isEmpty() || docentePos < 0) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val docenteId = listaDocentes[docentePos].id
            viewModel.insertarMateria(nombre, paralelo, docenteId)
            Toast.makeText(requireContext(), "Materia creada y asignada", Toast.LENGTH_SHORT).show()
            
            binding.etNombreMateria.text?.clear()
            binding.etParalelo.text?.clear()
        }

        viewModel.todasLasMaterias.observe(viewLifecycleOwner) { lista ->
            adapter.updateLista(lista)
        }
    }

    private fun setupViewModel() {
        val db = AppDatabase.getDatabase(requireContext())
        val repository = MateriaRepository(db.materiaDao())
        val usuarioRepo = UsuarioRepository(db.usuarioDao())
        
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return MateriaViewModel(repository, usuarioRepo) as T
            }
        })[MateriaViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = MateriaAdapter(emptyList()) { materia ->
        }
        binding.rvMaterias.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMaterias.adapter = adapter
    }

    private fun cargarDocentes() {
        lifecycleScope.launch {
            listaDocentes = viewModel.obtenerDocentes()
            val nombres = listaDocentes.map { it.nombre }
            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDocentes.adapter = spinnerAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
