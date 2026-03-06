package com.example.registroacademico.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registroacademico.model.entities.LoginResponse
import com.example.registroacademico.model.entities.Usuario
import com.example.registroacademico.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> get() = _registerResult

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    fun login(email: String, password: String) {
        val user = Usuario(email = email, password = password, nombre = "")
        RetrofitClient.instance.login(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && !body.error) {
                        _loginResult.value = true
                        _message.value = body.message
                    } else {
                        _loginResult.value = false
                        _message.value = body?.message ?: "Error en las credenciales"
                    }
                } else {
                    _loginResult.value = false
                    _message.value = "Error del servidor: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.value = false
                _message.value = "Error de red: ${t.message}"
            }
        })
    }

    fun register(name: String, email: String, password: String) {
        val user = Usuario(nombre = name, email = email, password = password)
        RetrofitClient.instance.register(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && !body.error) {
                        _registerResult.value = true
                        _message.value = body.message
                    } else {
                        _registerResult.value = false
                        _message.value = body?.message ?: "Error al registrar"
                    }
                } else {
                    _registerResult.value = false
                    _message.value = "Error del servidor: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _registerResult.value = false
                _message.value = "Error de red: ${t.message}"
            }
        })
    }
}
