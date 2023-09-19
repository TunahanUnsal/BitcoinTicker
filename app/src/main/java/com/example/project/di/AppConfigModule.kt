package com.example.project.di

import com.example.project.data.AppConfig
import com.example.project.util.ApiKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {
    @Singleton
    @Provides
    fun provideAppConfig(
        @ApiKey apiKey: String
    ) = AppConfig(
        apiKey = apiKey
    )
}