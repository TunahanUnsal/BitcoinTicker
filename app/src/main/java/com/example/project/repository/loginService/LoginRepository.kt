package com.example.project.repository.loginService

import com.example.project.repository.loginService.reqres.LoginRequest
import com.example.project.repository.loginService.reqres.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun login(req: LoginRequest): Flow<LoginResponse>

}