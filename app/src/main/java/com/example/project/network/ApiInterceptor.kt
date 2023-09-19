package com.example.project.network

import com.example.project.data.AppConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor(
    private val appConfig: AppConfig
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val newRequest: Request = originalRequest.newBuilder()
            .header("ApiKey", appConfig.apiKey)
            .build()

        return chain.proceed(newRequest)

    }

}