package com.example.project.network

import kotlinx.coroutines.flow.Flow

abstract class UseCase<P, R> {
    operator fun invoke(parameter: P?): Flow<R> = execute(parameter)

    protected abstract fun execute(parameter: P?): Flow<R>
}