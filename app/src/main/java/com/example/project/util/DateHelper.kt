package com.example.project.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateHelper {
    fun formatClientTimeDate(date: Date, format: String, timeZone: TimeZone?): String {
        val dateFormatter = SimpleDateFormat(format, Locale(APP_LANGUAGE, APP_COUNTRY))
        if (timeZone != null)
            dateFormatter.timeZone = timeZone
        return date.let { dateFormatter.format(it) }
    }
}