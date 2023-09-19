package com.example.project.repository.loginService.reqres

import com.example.project.network.ApiResponse
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    @SerializedName("IsSuccess")
    var isSuccess: Boolean
) : ApiResponse()