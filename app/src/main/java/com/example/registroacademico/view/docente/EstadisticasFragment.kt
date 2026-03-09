package com.example.registroacademico.view.docente

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.registroacademico.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class EstadisticasFragment : Fragment(R.layout.fragment_estadisticas) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAtras = view.findViewById<ImageButton>(R.id.btnAtrasEstadisticas)
        btnAtras.setOnClickListener {
            findNavController().navigateUp()
        }

        val pieChart = view.findViewById<PieChart>(R.id.pieChart)

        val entries = listOf(
            PieEntry(10f, "Presentes"),
            PieEntry(5f, "Ausentes")
        )

        val dataSet = PieDataSet(entries, "Asistencia")
        dataSet.colors = listOf(
            resources.getColor(android.R.color.holo_green_light, null),
            resources.getColor(android.R.color.holo_red_light, null)
        )

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.centerText = "Resumen"
        pieChart.animateY(1000)
        pieChart.invalidate()
    }
}
