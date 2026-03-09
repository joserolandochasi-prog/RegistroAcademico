package com.example.registroacademico.view.docente

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.R
import com.example.registroacademico.database.AppDatabase
import com.example.registroacademico.model.entities.Asistencia
import com.example.registroacademico.model.entities.DetalleAsistencia
import com.example.registroacademico.model.entities.Materia
import com.example.registroacademico.network.RetrofitClient
import com.example.registroacademico.repository.AsistenciaRepository
import com.example.registroacademico.repository.MateriaRepository
import com.example.registroacademico.repository.UsuarioRepository
import com.example.registroacademico.repository.EstudianteRepository
import com.example.registroacademico.utils.SessionManager
import com.example.registroacademico.view.adapters.EstudianteAdapter
import com.example.registroacademico.viewmodel.AsistenciaViewModel
import com.example.registroacademico.viewmodel.MateriaViewModel
import com.example.registroacademico.viewmodel.ViewModelFactory
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TomarAsistenciaFragment : Fragment(R.layout.fragment_tomar_asistencia) {

    private lateinit var viewModel: AsistenciaViewModel
    private lateinit var materiaViewModel: MateriaViewModel
    private lateinit var adapter: EstudianteAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var spinnerMaterias: Spinner
    private lateinit var sessionManager: SessionManager
    private lateinit var tvContadorEstudiantes: TextView
    
    private var listaMaterias: List<Materia> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        setupViewModels()

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerEstudiantes)
        val btnGuardar = view.findViewById<MaterialButton>(R.id.btnGuardarAsistencia)
        val btnVolver = view.findViewById<MaterialButton>(R.id.btnVolverMenu)
        spinnerMaterias = view.findViewById(R.id.spinnerMateriasAsistencia)
        progressBar = view.findViewById(R.id.progressBarTomar)
        tvContadorEstudiantes = view.findViewById(R.id.tvTitulo)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        
        cargarMateriasDocente()

        spinnerMaterias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (listaMaterias.isNotEmpty()) {
                    val materiaSeleccionada = listaMaterias[position]
                    cargarEstudiantesPorMateria(materiaSeleccionada.id)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnGuardar.setOnClickListener {
            if (::adapter.isInitialized) {
                guardarAsistencia()
            } else {
                Toast.makeText(requireContext(), "No hay estudiantes para registrar", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolver.setOnClickListener {
            findNavController().popBackStack()
        }

        observeViewModel()
    }

    private fun setupViewModels() {
        val db = AppDatabase.getDatabase(requireContext())
        val repository = AsistenciaRepository(
            RetrofitClient.apiService, 
            db.asistenciaDao(), 
            db.detalleAsistenciaDao()
        )
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[AsistenciaViewModel::class.java]
        
        val materiaRepo = MateriaRepository(db.materiaDao())
        val usuarioRepo = UsuarioRepository(db.usuarioDao())
        materiaViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return MateriaViewModel(materiaRepo, usuarioRepo) as T
            }
        })[MateriaViewModel::class.java]
    }

    private fun cargarMateriasDocente() {
        val docenteId = sessionManager.getUserId()
        materiaViewModel.obtenerMateriasPorDocente(docenteId).observe(viewLifecycleOwner) { materias ->
            listaMaterias = materias
            if (materias.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "No tienes materias asignadas", Toast.LENGTH_SHORT).show()
                return@observe
            }
            val nombres = materias.map { "${it.nombre} (${it.paralelo})" }
            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMaterias.adapter = spinnerAdapter
        }
    }

    private fun cargarEstudiantesPorMateria(materiaId: Int) {
        val db = AppDatabase.getDatabase(requireContext())
        val estudianteRepo = EstudianteRepository(db.estudianteDao(), db.inscripcionDao())
        
        lifecycleScope.launch {
            progressBar.isVisible = true
            val estudiantes = estudianteRepo.obtenerEstudiantesPorMateria(materiaId)
            
            if (::adapter.isInitialized) {
                adapter.actualizarLista(estudiantes)
            } else {
                adapter = EstudianteAdapter(estudiantes)
                val recycler = view?.findViewById<RecyclerView>(R.id.recyclerEstudiantes)
                recycler?.adapter = adapter
            }
            
            tvContadorEstudiantes.text = "Tomar Asistencia (${estudiantes.size} Estudiantes)"
            progressBar.isVisible = false
            
            if (estudiantes.isEmpty()) {
                Toast.makeText(requireContext(), "No hay estudiantes inscritos en este curso", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.guardadoExitoso.observe(viewLifecycleOwner) { exitoso ->
            if (exitoso) {
                Toast.makeText(requireContext(), "Asistencia guardada correctamente", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            progressBar.isVisible = loading
        }
    }

    private fun guardarAsistencia() {
        val materiaPos = spinnerMaterias.selectedItemPosition
        if (materiaPos < 0 || listaMaterias.isEmpty()) return
        
        val materia = listaMaterias[materiaPos]
        val asistenciasMarcadas = adapter.obtenerAsistencias()
        val totalEstudiantes = asistenciasMarcadas.keys.size
        val presentes = asistenciasMarcadas.values.count { it }
        
        val sdfFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val sdfHora = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val ahora = Date()
        
        val fechaStr = sdfFecha.format(ahora)
        val horaStr = sdfHora.format(ahora)
        
        // Incluimos la materia seleccionada en la asistencia
        val nuevaAsistencia = Asistencia(
            fecha = fechaStr, 
            hora = horaStr, 
            materiaId = materia.id, 
            nombreMateria = materia.nombre
        )
        
        val detalles = asistenciasMarcadas.map { (estudianteId, presente) ->
            DetalleAsistencia(
                asistenciaId = 0, 
                estudianteId = estudianteId,
                presente = presente
            )
        }

        if (detalles.isEmpty()) {
            Toast.makeText(requireContext(), "No hay estudiantes para registrar", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.guardarAsistenciaCompleta(nuevaAsistencia, detalles)
    }
}
