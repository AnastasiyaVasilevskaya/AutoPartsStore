package com.example.autond.domain.repository

import com.example.autond.domain.entity.Session
import com.example.autond.domain.entity.UserProfile
import com.example.autond.domain.util.Event
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun login(email: String, password: String): Event<Session>

    suspend fun signUp(email: String, password: String, username: String): Event<Session>

    suspend fun fetchToken(): Flow<String?>

    suspend fun saveToken(token: String)

    suspend fun saveUserProfile(userProfile: UserProfile)
}