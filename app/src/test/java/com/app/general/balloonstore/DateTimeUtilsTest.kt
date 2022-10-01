package com.app.general.balloonstore

import com.app.general.balloonstore.common.utils.formatDateTime
import org.junit.Test

import org.junit.Assert.*

class DateTimeUtilsTest {
    @Test
    fun formatDateTime_isCorrect() {
        val date1 = formatDateTime("2020-05-13T13:00:00Z", "dd MMM yyyy")
        assertEquals("13 May 2020", date1)

        val date2 = formatDateTime("2020-05-13T13:00:00Z", "dd MM yyyy")
        assertEquals("13 05 2020", date2)
    }
}