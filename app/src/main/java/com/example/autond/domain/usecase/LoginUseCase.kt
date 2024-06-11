package com.example.autond.domain.usecase

import com.example.autond.domain.entity.Session
import com.example.autond.domain.repository.SessionRepository
import com.example.autond.domain.util.Event
import com.example.autond.domain.util.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val sessionRepository: SessionRepository) :
    UseCase<LoginUseCase.Params, Session> {

    data class Params(
        val email: String,
        val password: String,
    )

    override suspend operator fun invoke(params: Params): Flow<Session> = flow {
        val email = params.email
        val password = params.password
        if (email.isNotEmpty() && password.isNotEmpty()) {
            val event =
                sessionRepository.login(email = email, password = password)
            when (event) {
                is Event.Success -> {
                    val session = event.data
                    sessionRepository.saveToken(session.token)
                    sessionRepository.saveUserProfile(session.userProfile)
                    emit(session)
                }

                is Event.Failure -> {
                    throw Exception(event.exception)
                }

            }
        } else {
            throw Exception("Email or password is wrong.")
        }
    }
}