package com.example.project.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.provider.Settings

object DeviceUtil {
    fun getDeviceModel() =
        "Android" + " | " + Build.MANUFACTURER + " | " + Build.MODEL + " | " + Build.VERSION.RELEASE

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return try {
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
            "System Error"
        }
    }

    fun getSoftwareVersion(context: Context): String {
        val pi: PackageInfo
        return try {
            pi = context.packageManager.getPackageInfo(context.packageName, 0)
            pi.versionName
        } catch (e: NameNotFoundException) {
            "na"
        }
    }

}