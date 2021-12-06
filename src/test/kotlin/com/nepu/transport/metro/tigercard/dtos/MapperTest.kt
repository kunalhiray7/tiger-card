package com.nepu.transport.metro.tigercard.dtos

import com.nepu.transport.metro.tigercard.domain.Trip
import com.nepu.transport.metro.tigercard.domain.Zone
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.ZoneId
import java.time.ZonedDateTime

class MapperTest {

    private val mapper = Mapper()

    @Test
    fun `should return the domain Trip object for given TripFareCalculationRequest`() {
        val request = TripFareCalculationRequest(1, Zone.ZONE_1, Zone.ZONE_1, ZonedDateTime.now(ZoneId.of("UTC")))

        val domainTrip = mapper.toDomainTrip(request)

        assertEquals(request.commuterId, domainTrip.commuterId)
        assertEquals(request.fromZone, domainTrip.fromZone)
        assertEquals(request.toZone, domainTrip.toZone)
        assertEquals(request.tripDateTime, domainTrip.tripDateTime)
    }

    @Test
    fun `should return the correct TripFareCalculationResponse object for given domain Trip`() {
        val trip = Trip(
                commuterId = 1,
                tripDateTime = ZonedDateTime.now(ZoneId.of("UTC")),
                fromZone = Zone.ZONE_1,
                toZone = Zone.ZONE_1,
                baseFare = 35,
                calculatedFare = 5,
                remark = "Base fare applied. Daily Cap Applied. ")

        val response = mapper.toTripFareCalculationResponse(trip)

        assertEquals(trip.commuterId, response.commuterId)
        assertEquals(trip.tripDateTime, response.tripDateTime)
        assertEquals(trip.fromZone, response.fromZone)
        assertEquals(trip.toZone, response.toZone)
        assertEquals(trip.baseFare, response.baseFare)
        assertEquals(trip.calculatedFare, response.calculatedFare)
        assertEquals(trip.remark, response.remark)
    }
}
