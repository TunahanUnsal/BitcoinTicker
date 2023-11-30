package com.ezdream.project.di

import com.ezdream.project.repository.coinService.CoinRepository
import com.ezdream.project.repository.coinService.CoinRepositoryImpl
import com.ezdream.project.repository.coinService.CoinService
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