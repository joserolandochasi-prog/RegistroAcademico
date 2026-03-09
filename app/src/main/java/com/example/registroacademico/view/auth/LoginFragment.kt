package com.example.registroacademico.view.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.registroacademico.R
import com.example.registroacademico.utils.SessionManager
import com.example.registroacademico.viewmodel.UsuarioViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: UsuarioViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvIrARegistro: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        btnLogin = view.findViewById(R.id.btnLogin)
        tvIrARegistro = view.findViewById(R.id.tvIrARegistro)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPassword.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(email, pass).observe(viewLifecycleOwner) { usuario ->
                if (usuario == null) {
                    Toast.makeText(
                        requireContext(),
                        "Credenciales incorrectas",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    sessionManager.saveUserId(usuario.id)
                    
                    when (usuario.rol) {
                        "admin" -> {
                            findNavController().navigate(R.id.action_loginFragment_to_adminDashboardFragment)
                        }
                        "docente" -> {
                            findNavController().navigate(R.id.docenteDashboardFragment)
                        }
                        "estudiante" -> {
                            findNavController().navigate(R.id.estudianteDashboardFragment)
                        }
                        "pendiente" -> {
                            findNavController().navigate(R.id.action_loginFragment_to_bienvenidaFragment)
                        }
                        else -> {
                            Toast.makeText(requireContext(), "Rol no reconocido", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        tvIrARegistro.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}
