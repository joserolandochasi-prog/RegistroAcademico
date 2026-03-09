package com.example.registroacademico.view.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registroacademico.database.AppDatabase
import com.example.registroacademico.databinding.FragmentGestionarEstudiantesBinding
import com.example.registroacademico.model.entities.Estudiante
import com.example.registroacademico.model.entities.Materia
import com.example.registroacademico.repository.EstudianteRepository
import com.example.registroacademico.repository.MateriaRepository
import com.example.registroacademico.repository.UsuarioRepository
import com.example.registroacademico.view.adapters.EstudianteAsignadoAdapter
import com.example.registroacademico.viewmodel.EstudianteViewModel
import com.example.registroacademico.viewmodel.MateriaViewModel

class GestionarEstudiantesFragment : Fragment() {

    private var _binding: FragmentGestionarEstudiantesBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var estudianteViewModel: EstudianteViewModel
    private lateinit var materiaViewModel: MateriaViewModel
    private lateinit var adapter: EstudianteAsignadoAdapter
    
    private var listaEstudiantes: List<Estudiante> = emptyList()
    private var listaMaterias: List<Materia> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGestionarEstudiantesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()
        setupRecyclerView()
        observeData()

        binding.btnAtrasEstudiantes.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnAsignarEstudiante.setOnClickListener {
            val estudiantePos = binding.spinnerEstudiantes.selectedItemPosition
            val materiaPos = binding.spinnerMaterias.selectedItemPosition

            if (estudiantePos < 0 || materiaPos < 0) {
                Toast.makeText(requireContext(), "Seleccione estudiante y materia", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val estudiante = listaEstudiantes[estudiantePos]
            val materia = listaMaterias[materiaPos]

            estudianteViewModel.inscribirEstudiante(estudiante.id, materia.id, materia.nombre, materia.paralelo)
            Toast.makeText(requireContext(), "Estudiante inscrito en ${materia.nombre}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViewModels() {
        val db = AppDatabase.getDatabase(requireContext())
        val studentRepo = EstudianteRepository(db.estudianteDao(), db.inscripcionDao())
        val materiaRepo = MateriaRepository(db.materiaDao())
        val userRepo = UsuarioRepository(db.usuarioDao())

        val factory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return when {
                    modelClass.isAssignableFrom(EstudianteViewModel::class.java) -> EstudianteViewModel(studentRepo) as T
                    modelClass.isAssignableFrom(MateriaViewModel::class.java) -> MateriaViewModel(materiaRepo, userRepo) as T
                    else -> throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }

        estudianteViewModel = ViewModelProvider(this, factory)[EstudianteViewModel::class.java]
        materiaViewModel = ViewModelProvider(this, factory)[MateriaViewModel::class.java]
        
        estudianteViewModel.cargarEstudiantes()
    }

    private fun setupRecyclerView() {
        adapter = EstudianteAsignadoAdapter()
        binding.rvEstudiantesAsignados.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEstudiantesAsignados.adapter = adapter
    }

    private fun observeData() {
        estudianteViewModel.estudiantes.observe(viewLifecycleOwner) { estudiantes ->
            listaEstudiantes = estudiantes
            val nombres = estudiantes.map { "${it.nombre} ${it.apellido}" }
            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerEstudiantes.adapter = spinnerAdapter
            
            adapter.updateLista(estudiantes)
        }

        materiaViewModel.todasLasMaterias.observe(viewLifecycleOwner) { materias ->
            listaMaterias = materias
            val nombres = materias.map { "${it.nombre} (${it.paralelo})" }
            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerMaterias.adapter = spinnerAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
