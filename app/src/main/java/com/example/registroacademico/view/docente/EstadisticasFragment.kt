package com.example.registroacademico.view.docente
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.registroacademico.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class EstadisticasFragment : Fragment(R.layout.fragment_estadisticas) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pieChart = view.findViewById<PieChart>(R.id.pieChart)

        val entries = listOf(
            PieEntry(10f, "Presentes"),
            PieEntry(5f, "Ausentes")
        )

        val dataSet = PieDataSet(entries, "Asistencia")

        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.invalidate()
    }
}