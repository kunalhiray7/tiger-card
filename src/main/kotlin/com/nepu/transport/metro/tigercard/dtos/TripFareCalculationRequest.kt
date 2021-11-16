package com.nepu.transport.metro.tigercard.dtos

import com.nepu.transport.metro.tigercard.domain.Trip
import com.nepu.transport.metro.tigercard.domain.Zone
import java.time.ZonedDateTime

data class TripFareCalculationRequest(
        val commuterId: Int,
        val fromZone: Zone,
        val toZone: Zone,
        val tripDateTime: ZonedDateTime
) {
    fun toDomainTrip() = Trip(
            commuterId = commuterId,
            fromZone = fromZone,
            toZone = toZone,
            tripDateTime = tripDateTime
    )
}
