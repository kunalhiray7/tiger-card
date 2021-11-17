package com.nepu.transport.metro.tigercard.dtos

import com.nepu.transport.metro.tigercard.domain.Trip

data class TripsFareCalculationResponse(
        val totalFareForAllTrips: Int,
        val trips: List<Trip>
)
