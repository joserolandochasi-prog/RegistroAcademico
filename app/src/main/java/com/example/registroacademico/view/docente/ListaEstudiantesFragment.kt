package com.example.registroacademico.view.docente

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.R
import com.example.registroacademico.viewmodel.ApiEstudiantesViewModel
import kotlinx.coroutines.launch

class ListaEstudiantesFragment : Fragment(R.layout.fragment_lista_estudiantes) {

    private val viewModel: ApiEstudiantesViewModel by viewModels()
    private lateinit var adapter: ListaEstudiantesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerListaEstudiantes)

        adapter = ListaEstudiantesAdapter(emptyList()) { estudiante ->
            Toast.makeText(requireContext(), "Eliminar: ${estudiante.nombre}", Toast.LENGTH_SHORT).show()
        }

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel.cargarDesdeApi()

        lifecycleScope.launch {
            viewModel.estudiantes.collect { lista ->
                adapter.actualizarLista(lista)
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { mensaje ->
                if (mensaje != null) {
                    Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}