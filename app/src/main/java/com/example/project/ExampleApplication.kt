package com.example.project

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExampleApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("ExampleApplication", "onCreate")
    }
}