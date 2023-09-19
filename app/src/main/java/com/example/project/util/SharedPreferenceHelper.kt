package com.example.project.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceHelper {
    fun getPrefs(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
}