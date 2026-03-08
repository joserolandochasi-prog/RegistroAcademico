package com.example.registroacademico.ui.docente

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.R
import com.example.registroacademico.model.entities.Estudiante

class TomarAsistenciaFragment : Fragment(R.layout.fragment_tomar_asistencia) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerEstudiantes)

        val lista = listOf(
            Estudiante(id = 1, nombre = "Juan Pérez"),
            Estudiante(id = 2, nombre = "María López"),
            Estudiante(id = 3, nombre = "Carlos Sánchez"),
            Estudiante(id = 4, nombre = "Ana Torres")
        )

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = EstudianteAdapter(lista)
    }
}