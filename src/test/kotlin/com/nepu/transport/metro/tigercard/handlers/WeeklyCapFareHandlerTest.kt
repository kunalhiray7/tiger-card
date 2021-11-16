package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip
import com.nepu.transport.metro.tigercard.domain.Zone
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
        handler.handleTrip(trips[0], trips)

        Mockito.verify(mockHandler, Mockito.times(1)).handleTrip(trips[0], trips)
    }
}
