package com.example.autond.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> get() = _signUpResult
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun signUp(email: String, phoneNumber: String, password: String) {
        viewModelScope.launch {
            if (email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                _errorMessage.value = "Please fill in all fields"
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = FirebaseAuth.getInstance().currentUser?.uid
                            val userInfo = hashMapOf(
                                "email" to email,
                                "phoneNumber" to phoneNumber,
                                "password" to password
                            )

                            userId?.let {
                                FirebaseDatabase.getInstance().getReference("Users")
                                    .child(it)
                                    .setValue(userInfo)
                                    .addOnCompleteListener { saveTask ->
                                        if (saveTask.isSuccessful) {
                                            _signUpResult.value = true
                                        } else {
                                            _signUpResult.value = false
                                            _errorMessage.value =
                                                saveTask.exception?.message ?: "Sign up failed"
                                        }
                                    }
                            } ?: run {
                                _errorMessage.value = "User ID is null"
                            }
                        } else {
                            _signUpResult.value = false
                            _errorMessage.value = task.exception?.message ?: "Sign up failed"
                        }
                    }
            }
        }
    }
}