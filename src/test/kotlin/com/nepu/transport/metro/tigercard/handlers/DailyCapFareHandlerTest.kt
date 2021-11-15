package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip
import com.nepu.transport.metro.tigercard.domain.Zone.ZONE_1
import com.nepu.transport.metro.tigercard.domain.Zone.ZONE_2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime

class DailyCapFareHandlerTest {

    private val fareCapHandler = DailyCapFareHandler()

    @Test
    fun `should return the fare without applying daily cap if cap is not reached`() {
        val trip1 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 7, 0,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_2
        )
        val trip2 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 7, 0,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_2
        )

        fareCapHandler.handle(listOf(trip1, trip2))

        Assertions.assertEquals(35, trip1.calculatedFare)
        Assertions.assertEquals(35, trip2.calculatedFare)
    }

    @Test
    fun `should return the fare with applying daily cap if cap is reached`() {
        val trip1 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_2,
                toZone = ZONE_1
        )
        val trip2 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 45,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip3 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 16, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip4 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 18, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip5 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 19, 0,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_2
        )

        fareCapHandler.handle(listOf(trip1, trip2, trip3, trip4, trip5))

        Assertions.assertEquals(35, trip1.calculatedFare)
        Assertions.assertEquals(25, trip2.calculatedFare)
        Assertions.assertEquals(25, trip3.calculatedFare)
        Assertions.assertEquals(30, trip4.calculatedFare)
        Assertions.assertEquals(5, trip5.calculatedFare)
    }

    @Test
    fun `should not apply fare to the trip if daily cap reached already`() {
        val trip1 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_2,
                toZone = ZONE_1
        )
        val trip2 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 45,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip3 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 16, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip4 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 18, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip5 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 19, 0,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_2
        )
        val trip6 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 19, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )

        fareCapHandler.handle(listOf(trip1, trip2, trip3, trip4, trip5, trip6))

        Assertions.assertEquals(0, trip6.calculatedFare)
    }

    @Test
    fun `should apply single zone cap if all the trips are within single zone`() {
        val trip1 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip2 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 45,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip3 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 16, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip4 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 18, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip5 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 19, 0,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )

        fareCapHandler.handle(listOf(trip1, trip2, trip3, trip4, trip5))

        Assertions.assertEquals(30, trip1.calculatedFare)
        Assertions.assertEquals(25, trip2.calculatedFare)
        Assertions.assertEquals(25, trip3.calculatedFare)
        Assertions.assertEquals(20, trip4.calculatedFare) // Applied cap = 100
        Assertions.assertEquals(0, trip5.calculatedFare)
    }

    @Test
    fun `should not apply daily cap for the trips on the other day`() {
        val trip1 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip2 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 45,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip3 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 16, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip4 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 18, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip5 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 9, 19, 0,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )
        val trip6 = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 9, 19, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_1,
                toZone = ZONE_1
        )

        fareCapHandler.handle(listOf(trip1, trip2, trip3, trip4, trip5, trip6))

        Assertions.assertEquals(30, trip1.calculatedFare)
        Assertions.assertEquals(25, trip2.calculatedFare)
        Assertions.assertEquals(25, trip3.calculatedFare)
        Assertions.assertEquals(20, trip4.calculatedFare)
        Assertions.assertEquals(30, trip5.calculatedFare)
        Assertions.assertEquals(30, trip6.calculatedFare)
    }

    @Test
    fun `should set the next handler correctly`() {
        val mockHandler = Mockito.mock(FareCapHandler::class.java)
        val trips = listOf(Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                        0, 0, ZoneId.of("UTC")),
                fromZone = ZONE_2,
                toZone = ZONE_1
        ))
        fareCapHandler.setNext(handler = mockHandler)
        fareCapHandler.handle(trips)

        Mockito.verify(mockHandler, Mockito.times(1)).handle(trips)
    }
}
