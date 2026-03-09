package com.example.registroacademico.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registroacademico.model.entities.Usuario

class AuthViewModel : ViewModel() {

    fun login(email: String, password: String): LiveData<Usuario?> {

        val result = MutableLiveData<Usuario?>()

        if (email == "docente@test.com" && password == "1234") {

            result.value = Usuario(
                id = 1,
                nombre = "Docente Test",
                email = "docente@test.com",
                password = "1234",
                rol = "docente"
            )

        } else if (email == "estudiante@test.com" && password == "1234") {

            result.value = Usuario(
                id = 2,
                nombre = "Estudiante Test",
                email = "estudiante@test.com",
                password = "1234",
                rol = "estudiante"
            )

        } else {

            result.value = null

        }

        return result
    }
}
