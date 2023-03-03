package com.ajmir.common.manager

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateManager {

    fun formatDate(
        date: Date,
        locale: Locale = Locale.getDefault()
    ): String {
        return try {
            DateFormat.getDateInstance(DateFormat.LONG, locale)
                .format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun formatTime(
        date: Date,
        locale: Locale = Locale.getDefault()
    ): String {
        return try {
            DateFormat.getTimeInstance(DateFormat.SHORT, locale)
                .format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun parse(
        date: String,
        locale: Locale = Locale.getDefault()
    ): Date? {
        return try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", locale).parse(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
