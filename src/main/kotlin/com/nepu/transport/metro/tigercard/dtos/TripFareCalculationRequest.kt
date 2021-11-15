package com.nepu.transport.metro.tigercard.dtos

import java.time.ZonedDateTime

data class TripFareCalculationRequest(
        val commuterId: Int,
        val fromZone: Int,
        val toZone: Int,
        val journeyDateTime: ZonedDateTime
)
