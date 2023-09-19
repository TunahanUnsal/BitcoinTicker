package com.example.project.domain.login

import com.example.project.network.UseCase
import com.example.project.repository.loginService.LoginRepository
import com.example.project.repository.loginService.reqres.LoginRequest
import com.example.project.repository.loginService.reqres.LoginResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) :
    UseCase<LoginUseCase.Params, LoginResponse>() {

    data class Params(
        val username: String,
        val password: String
    )

    override fun execute(params: Params): Flow<LoginResponse> {
        val req = LoginRequest(
            username = params.username,
            password = params.password
        )
        return repository.login(req)
    }

}