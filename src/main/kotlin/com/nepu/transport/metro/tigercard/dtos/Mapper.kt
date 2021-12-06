package com.nepu.transport.metro.tigercard.dtos

import com.nepu.transport.metro.tigercard.domain.Trip
import org.springframework.stereotype.Component

@Component
class Mapper {
    fun toDomainTrip(tripFareCalculationRequest: TripFareCalculationRequest) = Trip(
            commuterId = tripFareCalculationRequest.commuterId,
            fromZone = tripFareCalculationRequest.fromZone,
            toZone = tripFareCalculationRequest.toZone,
            tripDateTime = tripFareCalculationRequest.tripDateTime
    )

    fun toTripFareCalculationResponse(trip: Trip) = TripFareCalculationResponse(
            commuterId = trip.commuterId,
            tripDateTime = trip.tripDateTime,
            fromZone = trip.fromZone,
            toZone = trip.toZone,
            baseFare = trip.baseFare,
            calculatedFare = trip.calculatedFare,
            remark = trip.remark
    )
}
