package com.example.autond.domain.entity

data class UserProfile(
    val userId: String,
    val email: String,
    val username: String,
)

fun UserProfile.isValid(): Boolean = userId.isBlank()