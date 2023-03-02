package com.ajmir.common.manager

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateManager {

    fun formatDate(date: Date): String {
        return try {
            DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())
                .format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun formatTime(date: Date): String {
        return try {
            DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())
                .format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun parse(date: String): Date? {
        return try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault())
                .parse(date)
        } catch (e: Exception) {
            null
        }
    }
}