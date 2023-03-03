package com.ajmir.common

import com.ajmir.common.manager.DateManager
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.*

internal class DateManagerTest {

    private val dateManager = DateManager()

    // Friday, 3 March 2023 02:05:53
    val epocMilli = Instant.ofEpochMilli(1677805553000)
    val date = Date.from(epocMilli)

    @Test
    fun `formatDate for a given date should return a valid string`() {
        // When
        val result = dateManager.formatDate(date, Locale.FRANCE)
        // Then
        result shouldBe "3 mars 2023"
    }

    @Test
    fun `formatTime for a given date should return a valid string`() {
        // When
        val result = dateManager.formatTime(date, Locale.FRANCE)
        // Then
        result shouldBe "02:05"
    }
}
