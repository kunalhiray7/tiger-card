package com.nepu.transport.metro.tigercard.dtos

data class ConsolidatedTripsFareCalculationResponse(
        val totalFareForAllTrips: Int,
        val trips: List<TripFareCalculationResponse>
)
