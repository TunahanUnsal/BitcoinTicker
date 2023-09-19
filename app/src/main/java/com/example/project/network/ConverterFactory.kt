package com.example.project.network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class ConverterFactory constructor(val factory: GsonConverterFactory) : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val converter = factory.responseBodyConverter(type, annotations, retrofit)
        return ResponseBodyConverter(converter as Converter<ResponseBody, ApiResponse>, type)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return factory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }
}