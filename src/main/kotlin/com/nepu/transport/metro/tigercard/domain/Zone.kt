package com.nepu.transport.metro.tigercard.domain

import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_1_TO_ZONE_1_DAILY_CAP
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_1_TO_ZONE_1_OFF_PEAK_HOUR_FARE
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_1_TO_ZONE_1_PEAK_HOUR_FARE
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_1_TO_ZONE_1_WEEKLY_CAP
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_1_TO_ZONE_2_DAILY_CAP
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_1_TO_ZONE_2_OFF_PEAK_HOUR_FARE
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_1_TO_ZONE_2_PEAK_HOUR_FARE
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_1_TO_ZONE_2_WEEKLY_CAP
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_2_TO_ZONE_1_DAILY_CAP
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_2_TO_ZONE_1_OFF_PEAK_HOUR_FARE
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_2_TO_ZONE_1_PEAK_HOUR_FARE
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_2_TO_ZONE_1_WEEKLY_CAP
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_2_TO_ZONE_2_DAILY_CAP
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_2_TO_ZONE_2_OFF_PEAK_HOUR_FARE
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_2_TO_ZONE_2_PEAK_HOUR_FARE
import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.ZONE_2_TO_ZONE_2_WEEKLY_CAP
import com.nepu.transport.metro.tigercard.utils.PeakHoursDecider.Companion.isInPeakHours
import java.time.ZonedDateTime

enum class Zone {
    ZONE_1 {
        override fun getBaseFareTo(toZone: Zone, time: ZonedDateTime) =
                when (toZone) {
                    ZONE_1 -> if (isInPeakHours(time)) ZONE_1_TO_ZONE_1_PEAK_HOUR_FARE else ZONE_1_TO_ZONE_1_OFF_PEAK_HOUR_FARE
                    ZONE_2 -> if (isInPeakHours(time)) ZONE_1_TO_ZONE_2_PEAK_HOUR_FARE else ZONE_1_TO_ZONE_2_OFF_PEAK_HOUR_FARE
                }

        override fun getDailyCapTo(toZone: Zone) =
                when (toZone) {
                    ZONE_1 -> ZONE_1_TO_ZONE_1_DAILY_CAP
                    ZONE_2 -> ZONE_1_TO_ZONE_2_DAILY_CAP
                }

        override fun getWeeklyCapTo(toZone: Zone) =
                when (toZone) {
                    ZONE_1 -> ZONE_1_TO_ZONE_1_WEEKLY_CAP
                    ZONE_2 -> ZONE_1_TO_ZONE_2_WEEKLY_CAP
                }
    },

    ZONE_2 {
        override fun getBaseFareTo(toZone: Zone, time: ZonedDateTime) =
                when (toZone) {
                    ZONE_1 -> if (isInPeakHours(time)) ZONE_2_TO_ZONE_1_PEAK_HOUR_FARE else ZONE_2_TO_ZONE_1_OFF_PEAK_HOUR_FARE
                    ZONE_2 -> if (isInPeakHours(time)) ZONE_2_TO_ZONE_2_PEAK_HOUR_FARE else ZONE_2_TO_ZONE_2_OFF_PEAK_HOUR_FARE
                }

        override fun getDailyCapTo(toZone: Zone) =
                when (toZone) {
                    ZONE_1 -> ZONE_2_TO_ZONE_1_DAILY_CAP
                    ZONE_2 -> ZONE_2_TO_ZONE_2_DAILY_CAP
                }

        override fun getWeeklyCapTo(toZone: Zone) =
                when (toZone) {
                    ZONE_1 -> ZONE_2_TO_ZONE_1_WEEKLY_CAP
                    ZONE_2 -> ZONE_2_TO_ZONE_2_WEEKLY_CAP
                }
    };

    abstract fun getBaseFareTo(toZone: Zone, time: ZonedDateTime): Int
    abstract fun getDailyCapTo(toZone: Zone): Int
    abstract fun getWeeklyCapTo(toZone: Zone): Int
}
