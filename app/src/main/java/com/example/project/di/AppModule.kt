package com.example.project.di

import android.content.Context
import com.example.project.BuildConfig.API_KEY
import com.example.project.BuildConfig.API_URL
import com.example.project.util.ApiKey
import com.example.project.util.ApiUrl
import com.example.project.util.DeviceId
import com.example.project.util.DeviceModel
import com.example.project.util.DeviceUtil
import com.example.project.util.GsonProvider
import com.example.project.util.SoftwareVersion
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @DeviceId
    @Provides
    fun provideDeviceId(@ApplicationContext context: Context) = DeviceUtil.getDeviceId(context)

    @DeviceModel
    @Provides
    fun provideDeviceModel() = DeviceUtil.getDeviceModel()

    @SoftwareVersion
    @Provides
    fun provideSoftwareVersion(@ApplicationContext context: Context) =
        DeviceUtil.getSoftwareVersion(context)

    @ApiUrl
    @Provides
    fun provideApiUrl() = API_URL

    @ApiKey
    @Provides
    fun provideApiKey() = API_KEY

    @Singleton
    @Provides
    fun provideGson() = GsonProvider.gson



}