package com.nepu.transport.metro.tigercard.services

import com.nepu.transport.metro.tigercard.domain.Zone
import com.nepu.transport.metro.tigercard.dtos.TripFareCalculationRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime

class TripServiceTest {

    private val service = TripService()

    @Test
    fun `should handle the request trips and return the trips with calculated fare`() {
        val trip1 = TripFareCalculationRequest(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 20,
                        0, 0, ZoneId.of("UTC")),
                fromZone = Zone.ZONE_2,
                toZone = Zone.ZONE_1
        )
        val trip2 = TripFareCalculationRequest(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 10, 45,
                        0, 0, ZoneId.of("UTC")),
                fromZone = Zone.ZONE_1,
                toZone = Zone.ZONE_1
        )
        val trip3 = TripFareCalculationRequest(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 16, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = Zone.ZONE_1,
                toZone = Zone.ZONE_1
        )
        val trip4 = TripFareCalculationRequest(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 18, 15,
                        0, 0, ZoneId.of("UTC")),
                fromZone = Zone.ZONE_1,
                toZone = Zone.ZONE_1
        )
        val trip5 = TripFareCalculationRequest(
                commuterId = 1,
                tripDateTime = ZonedDateTime.of(2021, Month.NOVEMBER.value, 8, 19, 0,
                        0, 0, ZoneId.of("UTC")),
                fromZone = Zone.ZONE_1,
                toZone = Zone.ZONE_2
        )

        val tripsFareCalculationResponse = service.process(listOf(trip1, trip2, trip3, trip4, trip5))

        assertEquals(35, tripsFareCalculationResponse.trips[0].calculatedFare)
        assertEquals(25, tripsFareCalculationResponse.trips[1].calculatedFare)
        assertEquals(25, tripsFareCalculationResponse.trips[2].calculatedFare)
        assertEquals(30, tripsFareCalculationResponse.trips[3].calculatedFare)
        assertEquals(5, tripsFareCalculationResponse.trips[4].calculatedFare)
    }
}
