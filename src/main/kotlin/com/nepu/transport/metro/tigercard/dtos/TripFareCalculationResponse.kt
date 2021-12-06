package com.nepu.transport.metro.tigercard.dtos

import com.nepu.transport.metro.tigercard.domain.Zone
import java.time.ZonedDateTime

data class TripFareCalculationResponse(
        val commuterId: Int,
        val tripDateTime: ZonedDateTime,
        val fromZone: Zone,
        val toZone: Zone,
        val baseFare: Int,
        val calculatedFare: Int,
        val remark: String
)
