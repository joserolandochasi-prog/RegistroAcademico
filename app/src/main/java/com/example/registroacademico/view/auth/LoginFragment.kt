package com.example.registroacademico.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.registroacademico.R
import com.example.registroacademico.viewmodel.AuthViewModel


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewModel: AuthViewModel

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        btnLogin = view.findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString()
            val pass = etPassword.text.toString()

            viewModel.login(email, pass).observe(viewLifecycleOwner) { usuario ->

                if (usuario == null) {

                    Toast.makeText(
                        requireContext(),
                        "Credenciales incorrectas",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    if (usuario.rol == "docente") {

                        findNavController().navigate(R.id.docenteDashboardFragment)

                    } else {

                        findNavController().navigate(R.id.estudianteDashboardFragment)

                    }

                }

            }

        }

    }
}