package com.example.project

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExampleApplication :Application() {
    override fun onCreate() {
        super.onCreate()
    }
}