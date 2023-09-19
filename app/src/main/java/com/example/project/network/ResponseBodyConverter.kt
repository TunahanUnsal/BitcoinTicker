package com.example.project.network

import com.example.project.util.GsonProvider
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Type

class ResponseBodyConverter(
    private val converter: Converter<ResponseBody, ApiResponse>,
    private val type: Type
) : Converter<ResponseBody, Any> {
    override fun convert(value: ResponseBody): Any {

        val response = converter.convert(value)

        return response?.let { res ->
            if (res.header?.responseStatus == true) {
                val newResponseBody = GsonProvider.gson.toJson(res.responseBody)
                GsonProvider.gson.fromJson<ApiResponse?>(newResponseBody, type)
                    .apply {
                        this.header = res.header
                    }
            } else {
                throw Exception(res.header?.responseMessage)
            }
        } ?: run {
            throw Exception("Header Error")
        }

    }
}