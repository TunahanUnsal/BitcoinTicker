package com.example.project.di

import com.example.project.data.AppConfig
import com.example.project.network.API_TIMEOUT
import com.example.project.network.ApiInterceptor
import com.example.project.network.ConverterFactory
import com.example.project.util.ApiUrl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideApiInterceptor(
        appConfig: AppConfig
    ): ApiInterceptor = ApiInterceptor(appConfig = appConfig)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        apiInterceptor: ApiInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiInterceptor)
        .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        @ApiUrl apiUrl: String,
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient)
        .addConverterFactory(ConverterFactory(GsonConverterFactory.create(gson)))
        .build()

}