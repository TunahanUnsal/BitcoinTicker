package com.example.project.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class ApiRepository {
    fun <T> sendRequest(call: suspend () -> T): Flow<T> = flow {
        emit(call.invoke())
    }.flowOn(Dispatchers.IO)
}