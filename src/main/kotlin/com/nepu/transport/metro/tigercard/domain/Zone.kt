package com.nepu.transport.metro.tigercard.domain

import com.nepu.transport.metro.tigercard.utils.PeakHoursDecider.Companion.isInPeakHours
import java.time.ZonedDateTime

enum class Zone(value: Int) {
    ZONE_1(1) {
        override fun getBaseFareTo(toZone: Zone, time: ZonedDateTime) =
                when (toZone) {
                    ZONE_1 -> if (isInPeakHours(time)) 30 else 25
                    ZONE_2 -> if (isInPeakHours(time)) 35 else 30
                }

        override fun getDailyCapTo(toZone: Zone) =
                when (toZone) {
                    ZONE_1 -> 100
                    ZONE_2 -> 120
                }

        override fun getWeeklyCapTo(toZone: Zone) =
                when (toZone) {
                    ZONE_1 -> 500
                    ZONE_2 -> 600
                }
    },

    ZONE_2(2) {
        override fun getBaseFareTo(toZone: Zone, time: ZonedDateTime) =
                when (toZone) {
                    ZONE_1 -> if (isInPeakHours(time)) 35 else 30
                    ZONE_2 -> if (isInPeakHours(time)) 25 else 20
                }

        override fun getDailyCapTo(toZone: Zone) =
                when (toZone) {
                    ZONE_1 -> 120
                    ZONE_2 -> 80
                }

        override fun getWeeklyCapTo(toZone: Zone) =
                when (toZone) {
                    ZONE_1 -> 600
                    ZONE_2 -> 400
                }
    };

    abstract fun getBaseFareTo(toZone: Zone, time: ZonedDateTime): Int
    abstract fun getDailyCapTo(toZone: Zone): Int
    abstract fun getWeeklyCapTo(toZone: Zone): Int
}
