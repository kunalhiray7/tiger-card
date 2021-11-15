package com.nepu.transport.metro.tigercard.domain

import com.nepu.transport.metro.tigercard.domain.Zone.ZONE_1
import com.nepu.transport.metro.tigercard.domain.Zone.ZONE_2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime

class ZoneTest {

    private val tripTimeInPeakHours = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 7, 0,
            0, 0, ZoneId.of("UTC"))

    private val tripTimeInOffPeakHours = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 6, 59,
            0, 0, ZoneId.of("UTC"))

    @Test
    fun `zone_1 should return 30 as base fare to zone_1 during peak hours`() {
        val baseFare = ZONE_1.getBaseFareTo(ZONE_1, tripTimeInPeakHours)

        assertEquals(30, baseFare)
    }

    @Test
    fun `zone_1 should return 25 as base fare to zone_1 during off-peak hours`() {
        val baseFare = ZONE_1.getBaseFareTo(ZONE_1, tripTimeInOffPeakHours)

        assertEquals(25, baseFare)
    }

    @Test
    fun `zone_1 should return 35 as base fare to zone_2 during peak hours`() {
        val baseFare = ZONE_1.getBaseFareTo(ZONE_2, tripTimeInPeakHours)

        assertEquals(35, baseFare)
    }

    @Test
    fun `zone_1 should return 30 as base fare to zone_2 during off-peak hours`() {
        val baseFare = ZONE_1.getBaseFareTo(ZONE_2, tripTimeInOffPeakHours)

        assertEquals(30, baseFare)
    }

    @Test
    fun `zone_2 should return 25 as base fare to zone_2 during peak hours`() {
        val baseFare = ZONE_2.getBaseFareTo(ZONE_2, tripTimeInPeakHours)

        assertEquals(25, baseFare)
    }

    @Test
    fun `zone_2 should return 20 as base fare to zone_2 during off-peak hours`() {
        val baseFare = ZONE_2.getBaseFareTo(ZONE_2, tripTimeInOffPeakHours)

        assertEquals(20, baseFare)
    }

    @Test
    fun `zone_2 should return 35 as base fare to zone_1 during peak hours`() {
        val baseFare = ZONE_2.getBaseFareTo(ZONE_1, tripTimeInPeakHours)

        assertEquals(35, baseFare)
    }

    @Test
    fun `zone_2 should return 30 as base fare to zone_1 during off-peak hours`() {
        val baseFare = ZONE_2.getBaseFareTo(ZONE_1, tripTimeInOffPeakHours)

        assertEquals(30, baseFare)
    }

    @Test
    fun `zone_1 should return 100 as daily cap to zone_1`() {
        val dailyCap = ZONE_1.getDailyCapTo(ZONE_1)

        assertEquals(100, dailyCap)
    }

    @Test
    fun `zone_1 should return 120 as daily cap to zone_2`() {
        val dailyCap = ZONE_1.getDailyCapTo(ZONE_2)

        assertEquals(120, dailyCap)
    }

    @Test
    fun `zone_2 should return 120 as daily cap to zone_1`() {
        val dailyCap = ZONE_2.getDailyCapTo(ZONE_1)

        assertEquals(120, dailyCap)
    }

    @Test
    fun `zone_2 should return 80 as daily cap to zone_2`() {
        val dailyCap = ZONE_2.getDailyCapTo(ZONE_2)

        assertEquals(80, dailyCap)
    }

    @Test
    fun `zone_1 should return 500 as weekly cap to zone_1`() {
        val dailyCap = ZONE_1.getWeeklyCapTo(ZONE_1)

        assertEquals(500, dailyCap)
    }

    @Test
    fun `zone_1 should return 600 as weekly cap to zone_2`() {
        val dailyCap = ZONE_1.getWeeklyCapTo(ZONE_2)

        assertEquals(600, dailyCap)
    }

    @Test
    fun `zone_2 should return 600 as weekly cap to zone_1`() {
        val dailyCap = ZONE_2.getWeeklyCapTo(ZONE_1)

        assertEquals(600, dailyCap)
    }

    @Test
    fun `zone_2 should return 400 as weekly cap to zone_2`() {
        val dailyCap = ZONE_2.getWeeklyCapTo(ZONE_2)

        assertEquals(400, dailyCap)
    }
}
