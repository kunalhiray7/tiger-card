package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip
import com.nepu.transport.metro.tigercard.domain.Zone
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime

class WeeklyCapFareHandlerTest {

    private lateinit var handler: FareCapHandler

    @BeforeEach
    fun setUp() {
        handler = WeeklyCapFareHandler()
    }

    @Test
    fun `should set the next handler correctly`() {
        val mockHandler = Mockito.mock(FareCapHandler::class.java)
        val trips = listOf(Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                        0, 0, ZoneId.of("UTC")),
                fromZone = Zone.ZONE_2,
                toZone = Zone.ZONE_1
        ))
        handler.setNext(handler = mockHandler)
        handler.handleTrip(trips[0], trips, 0)

        Mockito.verify(mockHandler, Mockito.times(1)).handleTrip(trips[0], trips, 0)
    }

    @Test
    fun `should not apply weekly cap if the cap is not reached`() {
        val trips = listOf(Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                        0, 0, ZoneId.of("UTC")),
                fromZone = Zone.ZONE_2,
                toZone = Zone.ZONE_1,
                calculatedFare = 35
        ))

        handler.handleTrip(trips[0], trips, 0)

        assertEquals(35, trips[0].calculatedFare)
    }

    @Test
    fun `should apply weekly cap if the cap calculated fare exceeds the cap`() {
        val trips = listOf(
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        calculatedFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 9, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        calculatedFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 10, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        calculatedFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 11, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        calculatedFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 12, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_1,
                        calculatedFare = 30
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        calculatedFare = 35
                ))

        handler.handleTrip(trips[0], trips, 120)
        handler.handleTrip(trips[1], trips, 120)
        handler.handleTrip(trips[2], trips, 120)
        handler.handleTrip(trips[3], trips, 120)
        handler.handleTrip(trips[4], trips, 120)
        handler.handleTrip(trips[5], trips, 120)

        assertEquals(0, trips[5].calculatedFare)
    }

    @Test
    fun `should apply reduced fare when the cap is reached`() {
        val trips = listOf(
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 9, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 10, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 11, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 12, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_1,
                        baseFare = 30
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ))

        handler.handleTrip(trips[0], trips, 120)
        handler.handleTrip(trips[1], trips, 120)
        handler.handleTrip(trips[2], trips, 120)
        handler.handleTrip(trips[3], trips, 120)
        handler.handleTrip(trips[4], trips, 100)
        handler.handleTrip(trips[5], trips, 120)

        assertEquals(20, trips[5].calculatedFare)
    }

    @Test
    fun `should apply zero fare for all the days in the week after cap is reached`() {
        val trips = listOf(
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 9, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 10, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 11, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 12, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_1,
                        baseFare = 30
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 14, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ))

        handler.handleTrip(trips[0], trips, 120)
        handler.handleTrip(trips[1], trips, 120)
        handler.handleTrip(trips[2], trips, 120)
        handler.handleTrip(trips[3], trips, 120)
        handler.handleTrip(trips[4], trips, 100)
        handler.handleTrip(trips[5], trips, 120)
        handler.handleTrip(trips[6], trips, 120)

        assertEquals(0, trips[6].calculatedFare)
    }

    @Test
    fun `should not apply cap when the new week is started`() {
        val trips = listOf(
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 9, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 10, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 11, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 12, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_1,
                        baseFare = 30
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 13, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 14, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35
                ),
                Trip(
                        commuterId = 1,
                        tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 15, 10, 20,
                                0, 0, ZoneId.of("UTC")),
                        fromZone = Zone.ZONE_1,
                        toZone = Zone.ZONE_2,
                        baseFare = 35,
                        calculatedFare = 35
                ))

        handler.handleTrip(trips[0], trips, 120)
        handler.handleTrip(trips[1], trips, 120)
        handler.handleTrip(trips[2], trips, 120)
        handler.handleTrip(trips[3], trips, 120)
        handler.handleTrip(trips[4], trips, 100)
        handler.handleTrip(trips[5], trips, 120)
        handler.handleTrip(trips[6], trips, 120)
        handler.handleTrip(trips[7], trips, 120)

        assertEquals(35, trips[7].calculatedFare)
    }
}
