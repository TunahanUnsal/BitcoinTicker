package com.example.project.repository.loginService

import com.example.project.repository.loginService.reqres.LoginRequest
import com.example.project.repository.loginService.reqres.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}