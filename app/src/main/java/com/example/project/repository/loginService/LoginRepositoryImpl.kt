package com.example.project.repository.loginService

import com.example.project.network.ApiRepository
import com.example.project.repository.loginService.reqres.LoginRequest
import com.example.project.repository.loginService.reqres.LoginResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val service: LoginService) : ApiRepository(),
    LoginRepository {
    override fun login(req: LoginRequest): Flow<LoginResponse> = sendRequest {
        service.login(req)
    }
}