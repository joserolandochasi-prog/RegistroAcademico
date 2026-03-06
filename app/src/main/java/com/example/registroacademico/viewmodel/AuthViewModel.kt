package com.example.registroacademico.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun login(email: String, password: String) {
        // Lógica temporal para probar
        if (email == "admin@admin.com" && password == "1234") {
            _loginResult.value = true
        } else {
            _loginResult.value = false
        }
    }
}
