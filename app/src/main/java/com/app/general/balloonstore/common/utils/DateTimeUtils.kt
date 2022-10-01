package com.app.general.balloonstore.common.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * Format Date
 * @param timestamp : String
 * @param format : String
 * return String?
 */
fun formatDateTime(timestamp: String, format : String) : String? {
    return try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val targetFormat = SimpleDateFormat(format, Locale.ENGLISH)
        val date = originalFormat.parse(timestamp)
        if (date != null) {
            targetFormat.format(date)
        } else
            null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}