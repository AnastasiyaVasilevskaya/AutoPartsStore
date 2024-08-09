package com.example.autond.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun onLoginButtonClicked(email: String, password: String) {
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty()) {
                _errorMessage.value = "Please fill in all fields"
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _loginResult.value = true
                        } else {
                            _loginResult.value = false
                            _errorMessage.value = task.exception?.message ?: "Login failed"
                        }
                    }
            }
        }
    }
}

