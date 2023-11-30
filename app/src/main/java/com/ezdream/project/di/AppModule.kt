package com.ezdream.project.di

import android.content.Context
import com.ezdream.project.BuildConfig.API_KEY
import com.ezdream.project.BuildConfig.API_URL
import com.ezdream.project.util.ApiKey
import com.ezdream.project.util.ApiUrl
import com.ezdream.project.util.DeviceId
import com.ezdream.project.util.DeviceModel
import com.ezdream.project.util.DeviceUtil
import com.ezdream.project.util.GsonProvider
import com.ezdream.project.util.SoftwareVersion
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