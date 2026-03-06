package com.example.registroacademico

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registroacademico.databinding.ActivityMainBinding
import com.example.registroacademico.view.adapters.AsistenciaAdapter
import com.example.registroacademico.viewmodel.AsistenciaViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Aquí llamamos a tu ViewModel (asegúrate de que el nombre sea el correcto)
    private val viewModel: AsistenciaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Inicializamos el ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cambiamos 'binding.main' por 'binding.root'
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 2. Configuramos el RecyclerView y el Adapter
        val adapter = AsistenciaAdapter()
        binding.rvAsistencia.adapter = adapter
        binding.rvAsistencia.layoutManager = LinearLayoutManager(this)

        // 3. Observamos los datos del ViewModel
        viewModel.estudiantes.observe(this) { listaEstudiantes ->
            adapter.updateLista(listaEstudiantes)
        }

        // 4. Configuración del botón guardar
        binding.btnGuardar.setOnClickListener {
            val asistencias = adapter.getAsistenciasMarcadas()
            // Aquí puedes llamar a una función del viewModel para guardar en Room
            Toast.makeText(this, "Asistencias capturadas: ${asistencias.size}", Toast.LENGTH_SHORT).show()
        }
    }
}