package com.nepu.transport.metro.tigercard.utils

import java.time.LocalTime
import java.time.ZonedDateTime

class PeakHoursDecider {
    companion object {
        private val weekdays = setOf("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY")
        private val weekends = setOf("SATURDAY", "SUNDAY")

        private val minMorningPeakTimeForWeekDay = LocalTime.of(7, 0)
        private val maxMorningPeakTimeForWeekDay = LocalTime.of(10, 30)

        private val minEveningPeakTimeForWeekDay = LocalTime.of(17, 0)
        private val maxEveningPeakTimeForWeekDay = LocalTime.of(20, 0)

        private val minMorningPeakTimeForWeekendDay = LocalTime.of(9, 0)
        private val maxMorningPeakTimeForWeekendDay = LocalTime.of(11, 0)

        private val minEveningPeakTimeForWeekendDay = LocalTime.of(18, 0)
        private val maxEveningPeakTimeForWeekendDay = LocalTime.of(22, 0)

        fun isInPeakHours(tripDateTime: ZonedDateTime): Boolean {

            val tripDayOfWeek = tripDateTime.dayOfWeek
            val localTripTime = tripDateTime.toLocalTime()
            if(weekdays.contains(tripDayOfWeek.name)) {
                if((!localTripTime.isBefore(minMorningPeakTimeForWeekDay) && !localTripTime.isAfter(maxMorningPeakTimeForWeekDay)) ||
                        (!localTripTime.isBefore(minEveningPeakTimeForWeekDay) && !localTripTime.isAfter(maxEveningPeakTimeForWeekDay))) {
                    return true
                }
            }

            if(weekends.contains(tripDayOfWeek.name)) {
                if((!localTripTime.isBefore(minMorningPeakTimeForWeekendDay) && !localTripTime.isAfter(maxMorningPeakTimeForWeekendDay)) ||
                        (!localTripTime.isBefore(minEveningPeakTimeForWeekendDay) && !localTripTime.isAfter(maxEveningPeakTimeForWeekendDay))) {
                    return true
                }
            }
            return false
        }
    }
}
