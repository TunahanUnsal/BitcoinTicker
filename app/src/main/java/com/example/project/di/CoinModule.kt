package com.example.project.di

import com.example.project.repository.coinService.CoinRepository
import com.example.project.repository.coinService.CoinRepositoryImpl
import com.example.project.repository.coinService.CoinService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoinModule {

    @Singleton
    @Provides
    fun provideCoinService(retrofit: Retrofit): CoinService =
        retrofit.create(CoinService::class.java)

    @Singleton
    @Provides
    fun provideCoinRepository(service: CoinService): CoinRepository =
        CoinRepositoryImpl(service)

}