package com.nepu.transport.metro.tigercard.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.stream.Stream

class PeakHoursDeciderTest {

    @ParameterizedTest(name = "should \"{1}\" true if the input time \"{0}\" is between peak hours")
    @MethodSource("peakHoursTripArguments")
    fun `should return true if the input time is between peak hours`(
            tripTime: ZonedDateTime,
            expectedResult: Boolean
    ) {
        val actual = PeakHoursDecider.isInPeakHours(tripTime)

        Assertions.assertEquals(expectedResult, actual)
    }

    @ParameterizedTest(name = "should \"{1}\" false if the input time \"{0}\" is not between peak hours")
    @MethodSource("nonPeakHoursTripArguments")
    fun `should return false if the input time is not between peak hours`(
            tripTime: ZonedDateTime,
            expectedResult: Boolean
    ) {
        val actual = PeakHoursDecider.isInPeakHours(tripTime)

        Assertions.assertEquals(expectedResult, actual)
    }

    private companion object {
        @JvmStatic
        fun peakHoursTripArguments() = Stream.of(
                // Monday - Morning
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 7, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 30,
                        0, 0, ZoneId.of("UTC")), true),
                // Tuesday - Morning
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 9, 7, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 9, 10, 30,
                        0, 0, ZoneId.of("UTC")), true),
                // Wednesday - Morning
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 10, 7, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 10, 10, 30,
                        0, 0, ZoneId.of("UTC")), true),
                // Thursday - Morning
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 11, 7, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 11, 10, 30,
                        0, 0, ZoneId.of("UTC")), true),
                // Friday - Morning
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 12, 7, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 12, 10, 30,
                        0, 0, ZoneId.of("UTC")), true),
                // Saturday - Morning
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 9, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 11, 0,
                        0, 0, ZoneId.of("UTC")), true),
                // Sunday - Morning
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 14, 9, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 14, 11, 0,
                        0, 0, ZoneId.of("UTC")), true),

                // Monday - Evening
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 17, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 20, 0,
                        0, 0, ZoneId.of("UTC")), true),
                // Tuesday - Evening
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 9, 17, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 9, 20, 0,
                        0, 0, ZoneId.of("UTC")), true),
                // Wednesday - Evening
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 10, 17, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 10, 20, 0,
                        0, 0, ZoneId.of("UTC")), true),
                // Thursday - Evening
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 11, 17, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 11, 20, 0,
                        0, 0, ZoneId.of("UTC")), true),
                // Friday - Evening
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 12, 17, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 12, 20, 0,
                        0, 0, ZoneId.of("UTC")), true),
                // Saturday - Evening
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 18, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 22, 0,
                        0, 0, ZoneId.of("UTC")), true),
                // Sunday - Evening
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 14, 18, 0,
                        0, 0, ZoneId.of("UTC")), true),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 14, 22, 0,
                        0, 0, ZoneId.of("UTC")), true),
        )

        @JvmStatic
        fun nonPeakHoursTripArguments() = Stream.of(
                // Monday - Morning
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 6, 59,
                        59, 0, ZoneId.of("UTC")), false),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 30,
                        1, 0, ZoneId.of("UTC")), false),
                // Monday - Evening
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 16, 59,
                        59, 0, ZoneId.of("UTC")), false),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 20, 0,
                        1, 0, ZoneId.of("UTC")), false),

                // Saturday - Morning
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 8, 59,
                        59, 0, ZoneId.of("UTC")), false),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 11, 0,
                        1, 0, ZoneId.of("UTC")), false),
                // Saturday - Evening
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 17, 59,
                        59, 0, ZoneId.of("UTC")), false),
                Arguments.of(ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 22, 0,
                        1, 0, ZoneId.of("UTC")), false),

        )
    }
}
