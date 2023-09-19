package com.example.project.di

import com.example.project.repository.loginService.LoginRepository
import com.example.project.repository.loginService.LoginRepositoryImpl
import com.example.project.repository.loginService.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideLoginRepository(service: LoginService): LoginRepository =
        LoginRepositoryImpl(service)

}