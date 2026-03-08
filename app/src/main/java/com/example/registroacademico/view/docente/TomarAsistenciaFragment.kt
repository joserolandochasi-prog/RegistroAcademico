package com.example.registroacademico.view.docente

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.R
import com.example.registroacademico.model.entities.Asistencia
import com.example.registroacademico.model.entities.DetalleAsistencia
import com.example.registroacademico.model.entities.Estudiante
import com.example.registroacademico.view.adapters.EstudianteAdapter
import com.example.registroacademico.viewmodel.AsistenciaViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TomarAsistenciaFragment : Fragment(R.layout.fragment_tomar_asistencia) {

    private lateinit var adapter: EstudianteAdapter
    private val viewModel: AsistenciaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerEstudiantes)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarAsistencia)

        val lista = listOf(
            Estudiante(id = "1", nombre = "Juan", apellido = "Pérez"),
            Estudiante(id = "2", nombre = "María", apellido = "López"),
            Estudiante(id = "3", nombre = "Carlos", apellido = "Sánchez"),
            Estudiante(id = "4", nombre = "Ana", apellido = "Torres")
        )

        adapter = EstudianteAdapter(lista)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        btnGuardar.setOnClickListener {
            val mapaAsistencia = adapter.obtenerAsistencias()

            if (mapaAsistencia.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Marca al menos una asistencia",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            val asistencia = Asistencia(
                fecha = fechaActual,
                docenteId = 1
            )

            val detalles = lista.map { estudiante ->
                DetalleAsistencia(
                    asistenciaId = 0,
                    estudianteId = estudiante.id,
                    presente = mapaAsistencia[estudiante.id] ?: false
                )
            }

            viewModel.guardarAsistenciaConDetalles(asistencia, detalles) {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "Asistencia guardada correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}