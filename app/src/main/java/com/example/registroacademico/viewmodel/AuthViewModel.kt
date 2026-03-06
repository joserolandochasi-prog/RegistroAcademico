package com.example.registroacademico.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> get() = _registerResult

    fun login(email: String, password: String) {

        if (email == "admin@admin.com" && password == "1234") {
            _loginResult.value = true
        } else {
            _loginResult.value = false
        }
    }

    fun register(name: String, email: String, password: String) {

        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            _registerResult.value = true
        } else {
            _registerResult.value = false
        }
    }
}
